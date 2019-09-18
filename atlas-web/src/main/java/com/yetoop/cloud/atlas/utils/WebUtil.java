package com.yetoop.cloud.atlas.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.exception.AppException;

public class WebUtil {

	public static final String RESULT_CODE_SUCCESS = "0";
	public static final String RESULT_CODE_FAIL = "1";

	public static final String RESULT_CODE_NAME = "status";
	public static final String MSG_CODE_NAME = "msg";

	public static final String VERIFY_CODE = "verifyCode";
	public static final String RANDOM_STRING = "randomString4SendMsg";

	public static String hostAddress = null;

	// public static WxAccessToken wxAccessToken;
	//
	// public static WxAccessToken wxJsticket;

	public static String getHostAddress() throws UnknownHostException {
		if (StringUtil.isNullString(hostAddress)) {
			InetAddress addr;
			try {
				addr = InetAddress.getLocalHost();
				hostAddress = addr.getHostAddress().toString();// 获得本机IP
			} catch (UnknownHostException e) {
				throw e;
			}
		}

		return hostAddress;
	}

	public static void checkVerifyCode(HttpSession session, String codeFromPage) {
		if (StringUtil.isNullString(codeFromPage)) {
			throw new AppException(AppException.IntfAppCode.NULL_VERIFY_CODE);
		}
		String rightVerifyCode = (String) session.getAttribute(VERIFY_CODE);
		if (StringUtil.isNullString(rightVerifyCode)) {
			throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_CODE, "请先获取验证码");
		}
		if (!rightVerifyCode.toLowerCase().equals(codeFromPage.toLowerCase())) {
			throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_CODE);
		}

		session.removeAttribute(VERIFY_CODE);
	}

	public static String bulidSuccessJson() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(RESULT_CODE_NAME, RESULT_CODE_SUCCESS);
		return JSON.toJSONString(map);
	}

	public static String bulidSuccessJson(String json) {
		JSONObject o = JSON.parseObject(json);
		o.put(RESULT_CODE_NAME, RESULT_CODE_SUCCESS);
		return o.toJSONString();
	}

	public static String bulidSuccessJson(List<?> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", list);
		map.put(RESULT_CODE_NAME, RESULT_CODE_SUCCESS);

		return JSON.toJSONString(map);
	}

	public static String bulidSuccessJson(Object o) {
		String json = JSON.toJSONString(o);
		return bulidSuccessJson(json);

	}

	public static String buildFailJson(Object o) {

		Map<String, String> map = new HashMap<String, String>();
		map.put(RESULT_CODE_NAME, RESULT_CODE_FAIL);
		return JSON.toJSONString(map);
	}

	public static String buildFailJson(AppException e) {

		Map<String, String> map = new HashMap<String, String>();
		map.put(RESULT_CODE_NAME, RESULT_CODE_FAIL);
		map.put(MSG_CODE_NAME, e.getMsg());

		return JSON.toJSONString(map);
	}

	public static String buildFailJson(Exception e) {

		Map<String, String> map = new HashMap<String, String>();
		map.put(RESULT_CODE_NAME, RESULT_CODE_FAIL);
		map.put(MSG_CODE_NAME, e.getMessage());

		return JSON.toJSONString(map);
	}

	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

}
