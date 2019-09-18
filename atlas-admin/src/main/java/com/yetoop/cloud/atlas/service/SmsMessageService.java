package com.yetoop.cloud.atlas.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yetoop.cloud.atlas.common.SmsTypeKeyEnum;

public interface SmsMessageService {
	
	/**
	 * 发送短信验证码
	 * @param mobile 手机号
	 * @param key 类型key枚举 {@link SmsTypeKeyEnum}
	 * @param isCheckRegister 是否判断已经注册  check用的
	 * @exception RuntimeException 发送短信程序出错异常
	 * @return 发送结果 Map<String, Object> 其中key=state_code值{0=发送失败，1=发送成功,2=发送限制(操作过快等等限制)},key=msg 值为{提示消息}
	 */
	public Map<String, Object> sendMobileSms(String mobile, String key, int isCheckRegister, HttpSession session,
			HttpServletRequest request);
	
	/**
	 * 验证手机验证码是否正确
	 * @param validCode 验证码
	 * @param mobile 手机号
	 * @param key key 类型key枚举 {@link SmsTypeKeyEnum}
	 * @exception RuntimeException 手机号格式错误出错
	 * @return
	 */
	public boolean validSmsCode(String validCode, String mobile, String key, HttpSession session);
	
	/**
	 * 执行发送
	 * @param phone 
	 * @param param 
	 * @param datas 要发送的参数内容数组{1}{2}...
	 */
	public void onSend(String phone,String templateId,String[] datas);
	
	/**
	 * 根据参数获取发送短信的模板Id
	 * @param param
	 * @return
	 */
	public String getTemplateId(String param);
}
