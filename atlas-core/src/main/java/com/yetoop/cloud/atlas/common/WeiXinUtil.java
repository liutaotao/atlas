package com.yetoop.cloud.atlas.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yetoop.cloud.atlas.exception.AppException;

public class WeiXinUtil {

	private static final Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

	private static WxAccessToken wxAccessToken;
	private static WxAccessToken wxJsticket;

	/**
	 * 进行sha1签名
	 * 
	 * @param params
	 * @return
	 */
	public static String sha1Sign(Map<String, String> params) {
		String url = createLinkString(params);
		if (log.isDebugEnabled()) {
			log.debug("sha1Sign url:{}", url);
		}
		String sign = Sha1.encode(url);
		return sign;
	}

	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if ("sign".equals(key)) {
				continue;
			}

			if ("".equals(prestr)) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
		return prestr;
	}

	public static WxAccessToken getJsapiTicket(String appId, String appSecret) {
		if (wxJsticket == null) {
			wxJsticket = new WxAccessToken();
		}
		if (!wxJsticket.isValid()) {
			WxAccessToken wxAccessToken = WeiXinUtil.getWxAccessToken(appId, appSecret);
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ wxAccessToken.getAccessToken() + "&type=jsapi";
			String message = HttpClientUtil.httpget(url);
			try {
				JSONObject o = JSON.parseObject(message);

				String ticket = o.getString("ticket");
				String expires_in = o.getString("expires_in");
				Integer expiresIn = StringUtil.toInt(expires_in, 7200);
				wxJsticket.create(expiresIn, ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return wxJsticket;
	}

	public static Map<String, Object> wxWapLogin(String appId, String redirectUrl, String state) {
		if (log.isDebugEnabled()) {
			log.debug("wxWapLogin-->appId:{},redirectUrl:{},state：{}", appId, redirectUrl, state);
		}
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
				+ redirectUrl + "&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";
		if (log.isDebugEnabled()) {
			log.debug("wxWapLogin-->url:{}", url);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wxLoginUrl", url);
		return map;
	}

	public static Map<String, Object> queryWxUserInfo(String code, String appId, String appSecret) {
		if (log.isDebugEnabled()) {
			log.debug("queryWxUserInfo-->code:{},appId:{},appSecret:{}", code, appId, appSecret);
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret
				+ "&code=" + code + "&grant_type=authorization_code";
		String weixinJson = HttpClientUtil.httpget(url);
		if (log.isDebugEnabled()) {
			log.debug("queryWxUserInfo-->weixinJson:{}", weixinJson);
		}
		JSONObject jObject = JSON.parseObject(weixinJson);

		if (log.isDebugEnabled()) {
			log.debug("queryWxUserInfo-->tokenMap:{}", jObject);
		}
		String errcode = jObject.getString("errcode");
		if (!StringUtil.isNullString(errcode)) {
			String errmsg = jObject.getString("errmsg");
			throw new AppException(AppException.BusiAppCode.ERROR_WX_OPERATION, errmsg);
		}
		String openid = jObject.getString("openid");
		String refreshToken = jObject.getString("refresh_token");
		Map<String, Object> tokenMap = JSON.parseObject(weixinJson, Map.class);

		WxAccessToken token = WeiXinUtil.getWxAccessToken(appId, tokenMap, refreshToken);
		// 获取unionId（真正唯一） 2
		String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token.getAccessToken()
				+ "&openid=" + openid + "&lang=zh_CN";
		String userInfoJson = HttpClientUtil.httpget(userInfoUrl);
		if (log.isDebugEnabled()) {
			log.debug("queryWxUserInfo-->userInfoJson:" + userInfoJson);
		}
		Map<String, Object> userInfo = JSON.parseObject(userInfoJson, Map.class);
		if (log.isDebugEnabled()) {
			log.debug("queryWxUserInfo-->userInfo:" + userInfo);
		}
		errcode = StringUtil.toString(userInfo.get("errcode"));
		if (!StringUtil.isNullString(errcode)) {
			String errmsg = StringUtil.toString(userInfo.get("errmsg"));
			throw new AppException(AppException.BusiAppCode.ERROR_WX_OPERATION, errmsg);
		}
		return userInfo;
	}

	private static WxAccessToken getWxAccessToken(String appId, String appSecret) {
		if (wxAccessToken == null) {
			wxAccessToken = new WxAccessToken();
		}
		if (!wxAccessToken.isValid()) {
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
					+ "&secret=" + appSecret;
			if (log.isDebugEnabled()) {
				log.debug("url:{}", url);
			}

			String message = HttpClientUtil.httpget(url);
			try {
				JSONObject jo = JSON.parseObject(message);
				String accessToken = jo.getString("accessToken");
				String expires_in = jo.getString("expires_in");
				Integer expiresIn = StringUtil.toInt(expires_in, 7200);
				wxJsticket.create(expiresIn, accessToken);
				if (log.isDebugEnabled()) {
					log.debug("accessToken:{" + accessToken + "}");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (wxAccessToken.isValid()) {
			return wxAccessToken;
		} else {
			throw new AppException(AppException.BusiAppCode.ERROR_WX_OPERATION, "微信token无效");
		}
	}

	private static WxAccessToken getWxAccessToken(String appId, Map<String, Object> tokenMap, String refreshToken) {
		if (wxAccessToken == null) {
			wxAccessToken = new WxAccessToken();
			wxAccessToken.create(tokenMap);
		}
		if (!wxAccessToken.isValid()) {
			String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appId
					+ "&grant_type=refresh_token&refresh_token=" + refreshToken;
			if (log.isDebugEnabled()) {
				log.debug("getWxAccessToken-->url:{}", url);
			}
			String accessToken = HttpClientUtil.httpget(url);
			if (log.isDebugEnabled()) {
				log.debug("getWxAccessToken-->accessToken:" + accessToken);
			}
			Map<String, Object> accessTokenMap = JSON.parseObject(accessToken, Map.class);
			String errcode = StringUtil.toString(accessTokenMap.get("errcode"));
			if (!StringUtil.isNullString(errcode)) {
				String errmsg = StringUtil.toString(accessTokenMap.get("errmsg"));
				throw new AppException(AppException.BusiAppCode.ERROR_WX_OPERATION, errmsg);
			}
			wxAccessToken.create(accessTokenMap);
		}
		if (wxAccessToken.isValid()) {
			return wxAccessToken;
		} else {
			throw new AppException(AppException.BusiAppCode.ERROR_WX_OPERATION, "微信token无效");
		}
	}

}
