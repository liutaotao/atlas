package com.yetoop.cloud.atlas.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yetoop.cloud.atlas.dto.EsMemberDto;

public interface AtalasMemberService {
	
	/**
	 * 统一微信callBack接口
	 * @param request
	 * @param code
	 * @param state
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public Map<String, Object> wxCallBack(HttpServletRequest request, String code, String state, Integer memberId,
			String encryptKey);
	
	/**
	 * 修改会员头像
	 * @param memberId
	 * @param encryptKey
	 * @param memberFace
	 */
	public void updateMemberFace(Integer memberId, String encryptKey, String memberFace);
	
	/**
	 * 修改会员昵称
	 * @param memberId
	 * @param encryptKey
	 * @param nickName
	 */
	public void updateMemberNickName(Integer memberId, String encryptKey, String nickName);
	
	/**
	 * 获取会员信息
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public EsMemberDto getMemberInfo(Integer memberId, String encryptKey);
	
	/**
	 * 用户绑定微信
	 * @param request
	 * @param code
	 * @param state
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public EsMemberDto memberBindWx(HttpServletRequest request, String code, String state, Integer memberId,
			String encryptKey);

	/**
	 * 微信登录  (无member不进行注册)
	 * @param request
	 * @param code
	 * @param state
	 * @return
	 */
	public EsMemberDto memberWxLogin(HttpServletRequest request,String code,String state);
	
	/**
	 * 解除微信绑定
	 * @param memberId
	 * @param encryptKey
	 */
	public void loosenWxBind(Integer memberId, String encryptKey);
	
	/**
	 * 更换密码
	 * @param memberId
	 * @param encryptKey
	 * @param oldPwd
	 * @param pwd
	 * @param rePwd
	 * @return
	 */
	public EsMemberDto changeMemberPwd(Integer memberId, String encryptKey, String oldPwd, String pwd,
			String rePwd);
	
	/**
	 * 更换手机号
	 * @param session
	 * @param memberId
	 * @param encryptKey
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	public EsMemberDto changeMemberPhone(HttpSession session, Integer memberId, String encryptKey, String phone,
			String smsCode);
	
	/**
	 * 重置密码
	 * @param session
	 * @param phone
	 * @param password
	 * @param rePasswd
	 * @param smsCode
	 * @return
	 */
	public EsMemberDto retrievePassword(HttpSession session, String phone, String password, String rePasswd,
			String smsCode);
	
	/**
	 * 注册用户
	 * @param phone
	 * @param password
	 * @param license agree
	 * @param smsCode
	 * @param session
	 * @return
	 */
	public EsMemberDto memberRegister(String phone, String password, String license, String smsCode, HttpSession session);
	
	/**
	 * 发送短信验证码
	 * @param phone
	 * @param key 绑定手机:check 找回密码：update_password；注册：register
	 * @param isCheckRegister
	 * @param vCode
	 * @param session
	 * @param request
	 * @return
	 */
	public Map<String, Object> sendSmsCode(String phone, String key, Integer isCheckRegister, String vCode, HttpSession session,
			HttpServletRequest request);
	
	/**
	 * 用户登录并返回用户信息
	 * @param phone
	 * @param password
	 * @return
	 */
	public EsMemberDto memberLogin(String phone, String password);
	
	/**
	 * 检查手机在平台的状态
	 * @param phone
	 * @return 0手机号未注册  1该手机号已经存在
	 */
	public int checkMemberPhone(String phone);

}
