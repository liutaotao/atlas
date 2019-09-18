package com.yetoop.cloud.atlas.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.yetoop.cloud.atlas.dto.AsWorksDto;
import com.yetoop.cloud.atlas.dto.EsMemberDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.mq.MemberViewWorks;
import com.yetoop.cloud.atlas.service.AtlasUserService;
import com.yetoop.cloud.atlas.utils.WebUtil;

@Controller
@RequestMapping(value = "/user")
public class AtlasUserController {
	private static final Logger log = LoggerFactory.getLogger(AtlasUserController.class);

	@Autowired
	private AtlasUserService atlasUserService;

	@Autowired
	private com.yetoop.cloud.atlas.mq.producer.QueueSender queueSender;

	/**
	 * 获取微信config
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param url
	 */
	@RequestMapping(value = "/initWeixinConfig")
	@ResponseBody
	public void initWeixinConfig(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String url) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, String> config = this.atlasUserService.initWeixinConfig(url);
			json = WebUtil.bulidSuccessJson(config);
			out.write(json);
		} catch (AppException e) {
			log.error("initWeixinConfig", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("initWeixinConfig", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

	@RequestMapping(value = "/beginView")
	@ResponseBody
	@Deprecated
	public void beginView(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			Integer memberId, String viewCode) {

	}

	@RequestMapping(value = "/leaveView")
	@ResponseBody
	public void leaveView(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			Integer memberId, String viewCode, String transactionId) {
		try {
			MemberViewWorks memberViewWorks = new MemberViewWorks();
			memberViewWorks.createLeaveView(memberId, viewCode, transactionId, session);
			queueSender.send(memberViewWorks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/reactiveView")
	@ResponseBody
	public void reactiveView(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			Integer memberId, String viewCode, String transactionId) {
		try {
			MemberViewWorks memberViewWorks = new MemberViewWorks();
			memberViewWorks.createReactiveView(memberId, viewCode, transactionId, session);
			queueSender.send(memberViewWorks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/endView")
	@ResponseBody
	public void endView(HttpServletResponse response, HttpServletRequest request, HttpSession session, Integer memberId,
			String viewCode, String transactionId) {
		try {
			MemberViewWorks memberViewWorks = new MemberViewWorks();
			memberViewWorks.createEndView(memberId, viewCode, transactionId, session);
			queueSender.send(memberViewWorks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/coutinueView")
	@ResponseBody
	public void coutinueView(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			Integer memberId, String viewCode, Integer count, Integer viewSeconds, String transactionId) {
		try {
			MemberViewWorks memberViewWorks = new MemberViewWorks();
			memberViewWorks.createContinueView(memberId, viewCode, count, viewSeconds, transactionId, session);
			queueSender.send(memberViewWorks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/shareView")
	@ResponseBody
	public void shareView(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			Integer memberId, String viewCode, String platform, String transactionId) {
		try {
			MemberViewWorks memberViewWorks = new MemberViewWorks();
			memberViewWorks.createShareView(memberId, viewCode, transactionId, session);
			queueSender.send(memberViewWorks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存 设备信息，IP，地区等信息到session
	 * 
	 * @param os
	 *            系统
	 * @param model
	 *            手机型号
	 * @param browser
	 *            浏览器内核
	 * @param cip
	 *            ip地址
	 * @param cid
	 *            城市id
	 * @param cname
	 *            城市名称
	 */
	@RequestMapping(value = "/saveDeviceInfo")
	@ResponseBody
	public void saveDeviceInfo(HttpServletResponse response, HttpServletRequest request, HttpSession session, String os,
			String model, String browser, String cip, String cid, String cname) {
		if (log.isDebugEnabled()) {
			log.debug("saveDeviceInfo-->os:{},model:{},browser:{},cip:{},cid:{},cname:{}", os, model, browser, cip, cid,
					cname);
		}

		String json = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("sessionId", session.getId());
			jsonObj.put("os", StringUtils.isEmpty(os) ? "" : os);
			jsonObj.put("model", StringUtils.isEmpty(model) ? "" : model);
			jsonObj.put("browser", StringUtils.isEmpty(browser) ? "" : browser);
			jsonObj.put("cip", StringUtils.isEmpty(cip) ? "" : cip);
			jsonObj.put("cid", StringUtils.isEmpty(cid) ? "" : cid);
			jsonObj.put("cname", StringUtils.isEmpty(cname) ? "" : cname);
			if (log.isDebugEnabled()) {
				log.debug("deviceInfo:{}", jsonObj.toString());
			}

			session.setAttribute("deviceInfo", jsonObj.toString());

			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (Exception e) {
			log.error("saveDeviceInfo", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

	@RequestMapping(value = "/wxLoginCallBack")
	@ResponseBody
	public void wxLoginCallBack(HttpServletResponse response, HttpServletRequest request, String code, String state) {
		if (log.isDebugEnabled()) {
			log.debug("wxLoginCallBack-->code:{},state:{}", code, state);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			EsMemberDto memberDto = this.atlasUserService.wxLoginCallBack(request, code, state);
			json = WebUtil.bulidSuccessJson(memberDto);
			out.write(json);
		} catch (AppException e) {
			log.error("wxLoginCallBack", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("wxLoginCallBack", e);
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
	 * wap微信授权登录
	 * 
	 * @param response
	 * @param request
	 * @param session
	 */
	@RequestMapping(value = "/wxLogin")
	@ResponseBody
	public void wxLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> wxLoginMap = this.atlasUserService.wxLogin(session, request);
			json = WebUtil.bulidSuccessJson(wxLoginMap);
			out.write(json);
		} catch (AppException e) {
			log.error("wxLogin", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("wxLogin", e);
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
	 * 获取预览作品列表
	 * 
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param priviewCode
	 */
	@RequestMapping(value = "/loadPriviewWorksDetail")
	@ResponseBody
	public void loadPriviewWorksDetail(HttpServletResponse response, Integer memberId, String encryptKey,
			String priviewCode) {
		if (log.isDebugEnabled()) {
			log.debug("loadWorksDetail->memberId:{},encryptKey:{},priviewCode:{}", memberId, encryptKey, priviewCode);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsWorksDto worksDto = this.atlasUserService.queryArtworksByPriviewCode(memberId, encryptKey, priviewCode);
			json = WebUtil.bulidSuccessJson(worksDto);
			out.write(json);
		} catch (AppException e) {
			log.error("loadPriviewWorksDetail", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadPriviewWorksDetail", e);
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
	 * 获取分享链接作品列表
	 * 
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param priviewCode
	 */
	@RequestMapping(value = "/loadWorksDetail")
	@ResponseBody
	public void loadWorksDetail(HttpServletResponse response, HttpServletRequest request, HttpSession session,
			Integer memberId, String encryptKey, String viewCode) {
		if (log.isDebugEnabled()) {
			log.debug("session.getId():{}",session.getId());

			log.debug("loadWorksDetail->memberId:{},encryptKey:{},viewCode:{}", memberId, encryptKey, viewCode);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsWorksDto worksDto = this.atlasUserService.queryArtworksByViewCode(memberId, encryptKey, viewCode);

			try {
				MemberViewWorks memberViewWorks = new MemberViewWorks();
				memberViewWorks.createBeginView(memberId, viewCode, worksDto, session);
				queueSender.send(memberViewWorks);
			} catch (Exception e) {
				e.printStackTrace();
			}

			json = WebUtil.bulidSuccessJson(worksDto);
			if (log.isDebugEnabled()) {
				log.debug("loadWorksDetail->json:{}", json);
			}
			out.write(json);
		} catch (AppException e) {
			log.error("loadWorksDetail", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadWorksDetail", e);
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
