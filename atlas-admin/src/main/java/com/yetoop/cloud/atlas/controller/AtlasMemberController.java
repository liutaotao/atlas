package com.yetoop.cloud.atlas.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yetoop.cloud.atlas.dto.EsMemberDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtalasMemberService;
import com.yetoop.cloud.atlas.utils.WebUtil;

@Controller
@RequestMapping(value="/member")
public class AtlasMemberController {
	
	private static final Logger log = LoggerFactory.getLogger(AtlasMemberController.class);
	
	@Autowired
	private AtalasMemberService atalasMemberService;
	
	/**
	 * 修改会员头像
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param memberFace
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberFace")
	public void updateMemberFace(HttpServletResponse response, Integer memberId, String encryptKey,
			String memberFace) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasMemberService.updateMemberFace(memberId, encryptKey, memberFace);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("updateMemberFace", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("updateMemberFace", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 修改会员昵称
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param nickName
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberNickName")
	public void updateMemberNickName(HttpServletResponse response, Integer memberId, String encryptKey,
			String nickName) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasMemberService.updateMemberNickName(memberId, encryptKey, nickName);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("updateMemberNickName", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("updateMemberNickName", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 解除微信绑定
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/loosenWxBind")
	public void loosenWxBind(HttpServletResponse response, Integer memberId, String encryptKey) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasMemberService.loosenWxBind(memberId, encryptKey);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("loosenWxBind", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loosenWxBind", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 获取会员信息及画廊名称
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberInfo")
	public void getMemberInfo(HttpServletResponse response, Integer memberId, String encryptKey) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto member = this.atalasMemberService.getMemberInfo(memberId, encryptKey);
			json = WebUtil.bulidSuccessJson(member);
			out.write(json);
		} catch (AppException e) {
			log.error("getMemberInfo", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("getMemberInfo", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 *  pc用户微信回调接口 type:1为登录 2为绑定
	 * @param response
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/wxCallBack")
	public void wxCallBack(HttpServletResponse response, HttpServletRequest request, String code, String state,
			Integer memberId, String encryptKey) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> map = this.atalasMemberService.wxCallBack(request, code, state, memberId, encryptKey);
			json = WebUtil.bulidSuccessJson(map);
			out.write(json);
		} catch (AppException e) {
			log.error("wxCallBack", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("wxCallBack", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 更换密码
	 * @param response
	 * @param session
	 * @param oldPwd 当前密码
	 * @param pwd 新密码
	 * @param rePwd 再次新密码
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/changeMemberPwd")
	public void changeMemberPwd(HttpServletResponse response, HttpSession session,String oldPwd, String pwd,
			String rePwd, Integer memberId, String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("changeMemberPwd-->oldPwd:{},memberId:{},encryptKey:{},pwd:{},rePwd:{}", oldPwd, memberId,
					encryptKey, pwd, rePwd);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto member = this.atalasMemberService.changeMemberPwd(memberId, encryptKey, oldPwd, pwd, rePwd);
			json = WebUtil.bulidSuccessJson(member);
			out.write(json);
		} catch (AppException e) {
			log.error("changeMemberPwd", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("changeMemberPwd", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 更换手机号
	 * @param response
	 * @param session
	 * @param smsCode
	 * @param phone
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/changeMemberPhone")
	public void changeMemberPhone(HttpServletResponse response, HttpSession session, String smsCode,
			String phone, Integer memberId, String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("changeMemberPhone-->phone:{},memberId:{},encryptKey:{},smsCode:{}", phone, memberId, encryptKey,
					smsCode);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto member = this.atalasMemberService.changeMemberPhone(session, memberId, encryptKey, phone, smsCode);
			json = WebUtil.bulidSuccessJson(member);
			out.write(json);
		} catch (AppException e) {
			log.error("changeMemberPhone", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("changeMemberPhone", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 重置密码
	 * @param response
	 * @param session
	 * @param smsCode
	 * @param phone
	 * @param password
	 * @param rePasswd
	 */
	@ResponseBody
	@RequestMapping(value = "/retrievePassword")
	public void retrievePassword(HttpServletResponse response, HttpSession session, String smsCode,
			String phone, String password, String rePasswd) {
		if (log.isDebugEnabled()) {
			log.debug("retrievePassword-->phone:{},password:{},rePasswd:{},smsCode:{}", phone, password, rePasswd,
					smsCode);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto member = this.atalasMemberService.retrievePassword(session, phone, password, rePasswd, smsCode);
			json = WebUtil.bulidSuccessJson(member);
			out.write(json);
		} catch (AppException e) {
			log.error("retrievePassword", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("retrievePassword", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 用户注册
	 * @param response
	 * @param session
	 * @param license
	 * @param smsCode 短信验证码
	 * @param phone
	 * @param password
	 */
	@ResponseBody
	@RequestMapping(value = "/memberRegister")
	public void memberRegister(HttpServletResponse response, HttpSession session, String license, String smsCode,
			String phone, String password) {
		if (log.isDebugEnabled()) {
			log.debug("memberRegister-->phone:{},password:{},license:{},smsCode:{}", phone, password, license, smsCode);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto member = this.atalasMemberService.memberRegister(phone, password, license, smsCode, session);
			json = WebUtil.bulidSuccessJson(member);
			out.write(json);
		} catch (AppException e) {
			log.error("memberRegister", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("memberRegister", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 *  发送短信验证码
	 * @param response
	 * @param phone
	 * @param key
	 * @param isCheckRegister
	 * @param vCode
	 */
	@ResponseBody
	@RequestMapping(value = "/send-sms-code")
	public void sendSmsCode(HttpServletResponse response, HttpSession session, HttpServletRequest request, String phone,
			String key, Integer isCheckRegister, String vCode) {
		if (log.isDebugEnabled()) {
			log.debug("sendSmsCode-->phone:{},key:{},isCheckRegister:{},vCode:{}", phone, key, isCheckRegister, vCode);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> sendResult = this.atalasMemberService.sendSmsCode(phone, key, isCheckRegister, vCode,
					session, request);
			int stateCode = Integer.parseInt(sendResult.get("state_code").toString());
			switch (stateCode) {
				case 0:
					json = WebUtil.buildFailJson(sendResult);
					break;
				case 1:
					json = WebUtil.bulidSuccessJson();
					break;
				case 2:
					json = WebUtil.buildFailJson(sendResult);
					break;
				default:
					json = WebUtil.bulidSuccessJson(sendResult);
					break;
			}
			out.write(json);
		} catch (AppException e) {
			log.error("sendSmsCode", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("sendSmsCode", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}
	
	/**
	 * 用户登录
	 * 返回member
	 * @param response
	 * @param phone
	 * @param password
	 */
	@ResponseBody
	@RequestMapping(value = "/memberLogin")
	public void memberLogin(HttpServletResponse response, String phone, String password) {
		if (log.isDebugEnabled()) {
			log.debug("memberLogin-->phone:{},password：{}", phone, password);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto member = this.atalasMemberService.memberLogin(phone, password);
			json = WebUtil.bulidSuccessJson(member);
			out.write(json);
		} catch (AppException e) {
			log.error("memberLogin", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("memberLogin", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

	/**
	 * 查询用户手机在平台的状态 0手机号未注册 1该手机号已经存在
	 * 
	 * @param response
	 * @param phone
	 */
	@ResponseBody
	@RequestMapping(value = "/checkMemberPhone")
	public void checkMemberPhone(HttpServletResponse response, String phone) {
		if (log.isDebugEnabled()) {
			log.debug("checkMemberPhone-->phone:{}", phone);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			int state = this.atalasMemberService.checkMemberPhone(phone);
			Map<String, Object> map = new HashMap<String, Object>();
			if (state == 0) {
				map.put("state", 0);
				map.put("msg", "手机号未注册");
			} else {
				map.put("state", 1);
				map.put("msg", "该手机号已经存在！");
			}
			json = WebUtil.bulidSuccessJson(map);
			out.write(json);
		} catch (AppException e) {
			log.error("checkMemberPhone", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("checkMemberPhone", e);
			json = WebUtil.buildFailJson(e); 
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

}
