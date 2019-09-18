package com.yetoop.cloud.atlas.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.SmsTypeKeyEnum;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.EsSmsPlatform;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum;
import com.yetoop.cloud.atlas.domain.persistence.EsMemberMapper;
import com.yetoop.cloud.atlas.domain.persistence.EsSmsPlatformMapper;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtlasConfigService;
import com.yetoop.cloud.atlas.service.EsSmsPlatformService;
import com.yetoop.cloud.atlas.service.SmsMessageService;

import net.sf.json.JSONObject;

@Service("smsMessageService")
public class SmsMessageServiceImpl implements SmsMessageService {

	protected final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private EsSmsPlatformMapper smsPlatformMapper;
	@Autowired
	private EsMemberMapper memberManager;
	@Autowired
	private AtlasConfigService atlasConfigService;
	@Autowired
	private EsSmsPlatformService smsPlatformService;

	private static CCPRestSmsSDK restAPI;
	// 短信验证码session前缀
	private static final String SMS_CODE_PREFIX = "es_sms_";

	// 短信验证间隔时间session前缀
	private static final String INTERVAL_TIME_PREFIX = "es_interval_";

	// 发送时间间隔
	private static final double SEND_INTERVAL = 60d;

	// 商标前缀
	private static final String BRAND_PREFIX = "真艺网";

	// 短信超时时间前缀
	private static final String SENDTIME_PREFIX = "es_sendtime";

	// 短信过期时间
	private static final Long SMS_CODE_TIMEOUT = 120l;

	@Override
	public void onSend(String phone, String templateId, String[] datas) {
		this.init();
		Map<String, Object> result = new HashMap<String, Object>();
		result = restAPI.sendTemplateSMS(phone, templateId, datas);
		if ("000000".equals(result.get("statusCode"))) {
			logger.info("已发送短信：" + datas);
		} else {
			// 异常返回输出错误码和错误信息
			logger.info("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			throw new RuntimeException("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
		}
	}

	@Override
	public String getTemplateId(String param) {
		Map<String, Object> setting = null;
		if (setting == null) {
			setting = atlasConfigService.getConfigValueMap("sms_templateid");
		}
		return StringUtil.toString(setting.get(param), false);
	}

	/**
	 * 初始化发送短信SDK
	 */
	private void init() {
		Map<String, String> param = this.initParam();
		if (restAPI == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("init");
			}
			// 初始化SDK
			restAPI = new CCPRestSmsSDK();
			// ******************************注释*********************************************
			// *初始化服务器地址和端口 *
			// *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
			// *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
			// *******************************************************************************
			String serverIP = param.get(EnumTypeEnum.SmsMessageEnum.SMS_SERV_URL.getCode());
			String serverPort = param.get(EnumTypeEnum.SmsMessageEnum.SMS_SERV_PORT.getCode());
			restAPI.init(serverIP, serverPort);

			// ******************************注释*********************************************
			// *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
			// *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
			// *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
			// *******************************************************************************
			String accountSid = param.get(EnumTypeEnum.SmsMessageEnum.SMS_ACCOUNT_SID.getCode());
			String accountToken = param.get(EnumTypeEnum.SmsMessageEnum.SMS_AUTH_TOKEN.getCode());

			restAPI.setAccount(accountSid, accountToken);

			// ******************************注释*********************************************
			// *初始化应用ID *
			// *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
			// *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
			// *******************************************************************************
			String appId = param.get(EnumTypeEnum.SmsMessageEnum.SMS_APP_ID.getCode());
			restAPI.setAppId(appId);
			if (logger.isDebugEnabled()) {
				logger.debug("restAPI:{}" + restAPI);
			}
		}

	}

	/**
	 * 获取发送短信重要参数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> initParam() {
		EsSmsPlatform platform = this.smsPlatformMapper.queryOpenSmsPlatform();
		if (platform != null) {
			String config = platform.getConfig();
			JSONObject jsonObject = JSONObject.fromObject(config);
			Map<String, String> map = (Map<String, String>) JSONObject.toBean(jsonObject, Map.class);
			return map;
		} else {
			throw new RuntimeException("你可能还未安装任何短信网关组件或配置错误！");
		}
	}

	@Override
	public Map<String, Object> sendMobileSms(String mobile, String key, int isCheckRegister, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			// 防止 空值
			if (key == null || "".equals(key)) {

				// 默认为登录
				key = SmsTypeKeyEnum.LOGIN.toString();
			}
			// 如果手机号格式不对
			if (!StringUtil.isMobilePhone(mobile)) {
				result.put("state_code", 2);
				result.put("msg", "手机号码格式错误");
				return result;
			}

			// 判断是否允许可以发送
			if (!validIsCanSendSms(key, session)) {
				result.put("state_code", 2);
				result.put("msg", "您的操作过快，请休息一下");
				return result;
			}

			// 随机生成的动态码
			String dynamicCode = "" + (int) ((Math.random() * 9 + 1) * 100000);

			// 动态码短信内容
			String smsContent = "" + dynamicCode;

			// 1如果是登录
			if (key.equals(SmsTypeKeyEnum.LOGIN.toString())) {
				smsContent = "您的登录验证码为：" + dynamicCode + ", 如非本人操作，请忽略本短信 【" + BRAND_PREFIX + "】";

				// 校验手机是否注册过
				if (!validMobileIsRegister(mobile)) {
					result.put("state_code", 2);
					result.put("msg", "当前手机号没有绑定相关帐号");
					return result;
				}

				// 2如果是注册
			} else if (key.equals(SmsTypeKeyEnum.REGISTER.toString())) {
				smsContent = "您的注册验证码为：" + dynamicCode + ", 如非本人操作，请忽略本短信 【" + BRAND_PREFIX + "】";

				// 校验手机是否注册过
				if (validMobileIsRegister(mobile)) {
					result.put("state_code", 2);
					result.put("msg", "当前输入手机号码已绑定有帐号，可直接登录");
					return result;
				}

				// 3如果是找回密码
			} else if (key.equals(SmsTypeKeyEnum.BACKPASSWORD.toString())) {
				smsContent = "您正在尝试找回密码，验证码为：" + dynamicCode + ", 如非本人操作，请忽略本短信 【" + BRAND_PREFIX + "】";

				// 校验手机是否注册过
				if (!validMobileIsRegister(mobile)) {
					result.put("state_code", 2);
					result.put("msg", "当前手机号没有绑定相关帐号");
					return result;
				}

				// 4是绑定帐号
			} else if (key.equals(SmsTypeKeyEnum.BINDING.toString())) {
				smsContent = "您正在绑定手机号，验证码为：" + dynamicCode + ", 如非本人操作，请忽略本短信 【" + BRAND_PREFIX + "】";

				// 校验手机是否注册过
				if (validMobileIsRegister(mobile)) {
					result.put("state_code", 2);
					result.put("msg", "当前输入手机号码已绑定有帐号，请解绑后再绑定");
					return result;
				}

				// 5是修改密码
			} else if (key.equals(SmsTypeKeyEnum.UPDATE_PASSWORD.toString())) {
				smsContent = "您正在修改密码，验证码为：" + dynamicCode + ", 如非本人操作，请忽略本短信 【" + BRAND_PREFIX + "】";

				// 校验手机是否注册过
				if (!validMobileIsRegister(mobile)) {
					result.put("state_code", 2);
					result.put("msg", "没有找到该手机号绑定的账户");
					return result;
				}
				// 6是普通校验
			} else if (key.equals(SmsTypeKeyEnum.CHECK.toString())) {

				// 如果需要验证用户是否注册
				if (isCheckRegister == 1) {

					// 校验手机是否注册过
					if (!validMobileIsRegister(mobile)) {
						result.put("state_code", 2);
						result.put("msg", "没有找到该手机号绑定的账户");
						return result;
					}
				}

				smsContent = "您好，您的验证码为：" + dynamicCode + ", 如非本人操作，请忽略本短信 【" + BRAND_PREFIX + "】";
			} else {
				throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_TYPE);
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("code", dynamicCode);

			smsPlatformService.send(mobile, smsContent, param);

			// session中的格式是 前缀+key+手机号 例子: es_sms_login_13123456789
			String codeSessionKey = SMS_CODE_PREFIX + key + mobile;
			session.setAttribute(codeSessionKey, dynamicCode);
			session.setAttribute(INTERVAL_TIME_PREFIX + key, DateUtil.getDateline());
			session.setAttribute(SENDTIME_PREFIX + key + mobile, DateUtil.getDateline());
			logger.debug(smsContent);

			String ip = request.getServerName();
			logger.debug("已发送短信:内容:" + smsContent + ",手机号:" + mobile + ",ip:" + ip);

			result.put("state_code", 1);
			result.put("msg", "发送成功");
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.put("state_code", 0);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@Override
	public boolean validSmsCode(String validCode, String mobile, String key, HttpSession session) {
		// 如果手机号格式不对
		if (!StringUtil.isMobilePhone(mobile)) {
			throw new AppException(AppException.IntfAppCode.ERROR_PHONE);
		}

		// 防止 空值
		if (key == null || "".equals(key)) {
			// 默认为登录
			key = SmsTypeKeyEnum.LOGIN.toString();
		}

		// 如果验证码为空
		if (validCode == null || "".equals(validCode)) {
			return false;
		}
		String code = (String) session.getAttribute(SMS_CODE_PREFIX + key + mobile);

		// 验证码为空
		if (code == null) {
			return false;
		} else {

			// 忽略大小写 判断 不正确
			if (!code.equalsIgnoreCase(validCode)) {
				return false;
			}
		}

		// 新增优化 auth zjp 2016-12-13
		// 验证短信是否超时
		Long sendtime = (Long) session.getAttribute(SENDTIME_PREFIX + key + mobile);
		Long checktime = DateUtil.getDateline();
		// 验证session但中是否存在当前注册用户的验证码
		if (sendtime == null) {
			return false;
		}
		if ((checktime - sendtime >= SMS_CODE_TIMEOUT)) {
			throw new RuntimeException("验证码超时");
		}
		// 验证通过后 去除session信息
		session.removeAttribute(SMS_CODE_PREFIX + key + mobile);
		return true;
	}

	/**
	 * 验证手机号有没有注册
	 * 
	 * @param mobile
	 *            手机号
	 * @exception RuntimeException
	 *                手机号格式错误出错
	 * @return boolean false=没有注册 true=注册了
	 */
	public boolean validMobileIsRegister(String mobile) {

		// 如果手机号格式不对
		if (!StringUtil.isMobilePhone(mobile)) {
			throw new AppException(AppException.IntfAppCode.ERROR_PHONE);
		}

		boolean isExists = memberManager.checkMobile(mobile) > 0 ? true : false;
		return isExists;
	}

	/**
	 * 验证是否可以发送信息(做倒计时判断，同一种类型加以校验)
	 * 
	 * @param key
	 *            类型key枚举 {@link SmsTypeKeyEnum}
	 * @return true=允许发送 false=不允许
	 */
	private boolean validIsCanSendSms(String key, HttpSession session) {

		// 当前时间
		Long now = DateUtil.getDateline();

		// session加上指定前缀
		Long lastGenTime = (Long) session.getAttribute(INTERVAL_TIME_PREFIX + key);

		// 如果lastGenTime不存在，即是第一次发送，允许发送；
		// 如果发送间隔已超出限定间隔时间，允许发送；
		if (lastGenTime == null || CurrencyUtil.sub(now, lastGenTime) >= SEND_INTERVAL) {
			return true;
		} else {
			return false;
		}

	}
}
