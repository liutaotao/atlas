package com.yetoop.cloud.atlas.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsGallary;
import com.yetoop.cloud.atlas.domain.AsMaterial;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.WxConfigTypeEnum;
import com.yetoop.cloud.atlas.dto.AsArtworkDto;
import com.yetoop.cloud.atlas.dto.AsWorksDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtalasAdminService;
import com.yetoop.cloud.atlas.utils.WebUtil;

@Controller
@RequestMapping(value="/admin")
public class AtlasAdminController {
	
	private static final Logger log = LoggerFactory.getLogger(AtlasAdminController.class);
	
	@Autowired
	private AtalasAdminService atalasAdminService;
	
	/**
	 * 修改画廊名称
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param gallaryName
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyGallaryName")
	public void modifyGallaryName(HttpServletResponse response, Integer memberId, String encryptKey,String gallaryName){
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.modifyGallaryName(memberId, encryptKey, gallaryName);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("modifyGallaryName", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("modifyGallaryName", e);
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
	 * 恢复作品集
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/restoreWorks")
	public void restoreWorks(HttpServletResponse response, Integer memberId, String encryptKey, Integer worksId){
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.restoreWorks(memberId, encryptKey, worksId);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("restoreWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("restoreWorks", e);
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
	 * 彻底删除作品集
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteWorks")
	public void deleteWorks(HttpServletResponse response, Integer memberId, String encryptKey, Integer worksId) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.deleteWorks(memberId, encryptKey, worksId);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("deleteWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("deleteWorks", e);
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
	 * 创建作品集副本
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/createReplicaWorks")
	public void createReplicaWorks(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.createReplicaWorks(memberId, encryptKey, worksId);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("createReplicaWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("createReplicaWorks", e);
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
	 * 修改作品集权限
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 * @param permissionCode  private:私有 link:公开链接 
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyWorksPermission")
	public void modifyWorksViewPermission(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, String permissionCode) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsWorks works = this.atalasAdminService.modifyWorksViewPermission(memberId, encryptKey, worksId,
					permissionCode);
			json = WebUtil.bulidSuccessJson(works);
			out.write(json);
		} catch (AppException e) {
			log.error("modifyWorksViewPermission", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("modifyWorksViewPermission", e);
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
	 * 获取绑定微信配置
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/initWxPcBindConfig")
	public void initWxPcBindConfig(HttpServletResponse response, HttpSession session) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> wxConfig = this.atalasAdminService.initWxPcBindConfig(session);
			json = WebUtil.bulidSuccessJson(wxConfig);
			out.write(json);
		} catch (AppException e) {
			log.error("initWxPcBindConfig", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("initWxPcBindConfig", e);
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
	 * 获取微信登录配置
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/initWxPcLoginConfig")
	public void initWxPcLoginConfig(HttpServletResponse response, HttpSession session) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> wxConfig = this.atalasAdminService.initWxPcLoginConfig(WxConfigTypeEnum.PCWXLOGIN.getType(),
					session);
			json = WebUtil.bulidSuccessJson(wxConfig);
			out.write(json);
		} catch (AppException e) {
			log.error("initWxPcLoginConfig", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("initWxPcLoginConfig", e);
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
	 * 查询回收站作品集列表并按seq倒序
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/loadTrashWorksList")
	public void loadTrashWorksList(HttpServletResponse response, Integer memberId,String encryptKey){
		if (log.isDebugEnabled()) {
			log.debug("loadTrashWorksList-->memberId:{},encryptKey:{}", memberId, encryptKey);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsWorksDto> trashWorksList = this.atalasAdminService.queryTrashWorks(memberId, encryptKey);
			json = WebUtil.bulidSuccessJson(trashWorksList);
			out.write(json);
		} catch (AppException e) {
			log.error("loadTrashWorksList", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadTrashWorksList", e);
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
	 * 删除作品
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 * @param artworkId 作品Id
	 */
	@ResponseBody
	@RequestMapping(value = "/removeArtwork")
	public void removeArtwork(HttpServletResponse response, Integer memberId, String encryptKey, Integer worksId,
			Integer artworkId) {
		if (log.isDebugEnabled()) {
			log.debug("removeArtwork-->memberId:{},encryptKey:{},worksId:{},artworkId:{}", memberId, encryptKey,
					worksId, artworkId);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.removeArtwork(memberId, encryptKey, worksId, artworkId);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("removeArtwork", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("removeArtwork", e);
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
	 * 发布反馈信息
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param detail 反馈信息
	 */
	@ResponseBody
	@RequestMapping(value = "/addFeedBack")
	public void createFeedBack(HttpServletResponse response, Integer memberId, String encryptKey, String detail) {
		if (log.isDebugEnabled()) {
			log.debug("createFeedBack-->memberId:{},encryptKey:{},detail:{}", memberId, encryptKey, detail);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.saveFeedBack(memberId, encryptKey, detail);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("createFeedBack", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("createFeedBack", e);
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
	 * 查询用户管理的画廊信息
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMemberGallery")
	public void queryMemberGallery(HttpServletResponse response,Integer memberId,String encryptKey){
		if (log.isDebugEnabled()) {
			log.debug("queryMemberGallery-->memberId:{},encryptKey:{}", memberId, encryptKey);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsGallary asGallary = this.atalasAdminService.queryMemberGallery(memberId, encryptKey);
			json = WebUtil.bulidSuccessJson(asGallary);
			out.write(json);
		} catch (AppException e) {
			log.error("queryMemberGallery", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("queryMemberGallery", e);
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
	 * 修改作品集名称并返回作品集
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 * @param worksName 作品集名称
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyWorksName")
	public void modifyWorksName(HttpServletResponse response,Integer memberId,String encryptKey,Integer worksId,String worksName){
		if (log.isDebugEnabled()) {
			log.debug("modifyWorksName-->memberId:{},encryptKey:{},worksId:{},worksName:{}", memberId, encryptKey,worksId,worksName);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsWorks asWorks = this.atalasAdminService.modifyWorksName(memberId, encryptKey, worksId, worksName);
			json = WebUtil.bulidSuccessJson(asWorks);
			out.write(json);
		} catch (AppException e) {
			log.error("modifyWorksName", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("modifyWorksName", e);
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
	 * 修改作品排序
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 * @param artworkIds 作品Ids 按顺序排列
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyArtworkSeq")
	public void modifyArtworkSeq(HttpServletResponse response,Integer memberId,String encryptKey,Integer worksId,Integer[] artworkIds){
		if (log.isDebugEnabled()) {
			log.debug("modifyArtworkSeq-->memberId:{},encryptKey:{},worksId:{},artworkIds:{}", memberId, encryptKey,worksId,artworkIds);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.modifyArtworkSeq(memberId, encryptKey, worksId, artworkIds);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("modifyArtworkSeq", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("modifyArtworkSeq", e);
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
	 * 生成预览链接
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/createPreviewUrl")
	public void createPreviewUrl(HttpServletResponse response,Integer memberId,String encryptKey,Integer worksId){
		if (log.isDebugEnabled()) {
			log.debug("createPreviewUrl-->memberId:{},encryptKey:{},worksId:{}", memberId, encryptKey,worksId);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			String previewUrl = this.atalasAdminService.modifyWorks4Priview(memberId, encryptKey, worksId);
			if(log.isDebugEnabled()){
				log.debug("createPreviewUrl-->previewUrl:{}",previewUrl);
			}
			json = WebUtil.bulidSuccessJson("{\"previewUrl\":\"" + previewUrl + "\"}");
			out.write(json);
		} catch (AppException e) {
			log.error("createPreviewUrl", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("createPreviewUrl", e);
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
	 * 删除作品集
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/removeWorks")
	public void removeWorks(HttpServletResponse response,Integer memberId,String encryptKey,Integer worksId){
		if (log.isDebugEnabled()) {
			log.debug("removeWorks-->memberId:{},encryptKey:{},worksId:{}", memberId, encryptKey,worksId);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			this.atalasAdminService.removeWorks(memberId, encryptKey, worksId);
			json = WebUtil.bulidSuccessJson();
			out.write(json);
		} catch (AppException e) {
			log.error("removeWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("removeWorks", e);
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
	 * 添加作品
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 * @param imageUrl 图片url
	 * @param artistName 艺术家名称
	 * @param artistId 艺术家Id
	 * @param artworkName 作品名
	 * @param materialId 材质Id
	 * @param length
	 * @param hight
	 * @param depth
	 * @param price 价格
	 * @param createYear 创建年份
	 * @param material 材质
	 * @param dimension 尺寸
	 */
	@ResponseBody
	@RequestMapping(value = "/addArtwork")
	public void createArtwork(HttpServletResponse response, Integer memberId, String encryptKey, Integer worksId,
			String imageUrl, String artistName, Integer artistId, String artworkName, Integer materialId,
			Integer length, Integer hight, Integer depth, Double price, Integer createYear, String material,
			String dimension) {
		if (log.isDebugEnabled()) {
			log.debug("createArtwork-->memberId:{},encryptKey:{},worksId:{},imageUrl:{},artistName:{},artistId:{},"
					+ "artworkName:{},materialId:{},length:{},hight:{},depth:{},price:{},createYear:{},material:{},dimension:{}",
					memberId, encryptKey, worksId, imageUrl, artistName, artistId, artworkName, materialId, length,
					hight, depth, price, createYear, material, dimension);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> artworkInfo = this.getArtworkInfo(imageUrl, artistName, artistId, artworkName,
					materialId, length, hight, depth, price, createYear, material, dimension);
			Integer artworkId = this.atalasAdminService.createArtwork(memberId, encryptKey, worksId, artworkInfo);
			json = WebUtil.bulidSuccessJson("{\"artworkId\":" + artworkId + "}");
			out.write(json);
		} catch (AppException e) {
			log.error("createArtwork", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("createArtwork", e);
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
	 * 根据作品集Id获取该作品集所有的作品
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllArtWorks")
	public void queryArtWorks(HttpServletResponse response,Integer memberId,String encryptKey,Integer worksId){
		if (log.isDebugEnabled()) {
			log.debug("queryArtWorks-->memberId:{},encryptKey:{},worksId:{}", memberId, encryptKey,worksId);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsArtworkDto> allArtworkDto = this.atalasAdminService.loadAllArtworkDto(memberId, encryptKey, worksId);
			json = WebUtil.bulidSuccessJson(allArtworkDto);
			out.write(json);
		} catch (AppException e) {
			log.error("queryArtWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("queryArtWorks", e);
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
	 * 根据作品集Id获取管理员管理的作品集
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/queryWorks")
	public void queryWorks(HttpServletResponse response,Integer memberId,String encryptKey,Integer worksId){
		if (log.isDebugEnabled()) {
			log.debug("queryWorks-->memberId:{},encryptKey:{},worksId:{}", memberId, encryptKey,worksId);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsWorks asWorks = this.atalasAdminService.queryWorks(memberId, encryptKey, worksId);
			json = WebUtil.bulidSuccessJson(asWorks);
			out.write(json);
		} catch (AppException e) {
			log.error("queryWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("queryWorks", e);
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
	 * 创建作品集并返回作品集Id
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 */
	@ResponseBody
	@RequestMapping(value = "/addWorks")
	public void createWorks(HttpServletResponse response,Integer memberId,String encryptKey){
		if (log.isDebugEnabled()) {
			log.debug("createWorks-->memberId:{},encryptKey:{}", memberId, encryptKey);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Integer worksId = this.atalasAdminService.createWorks(memberId, encryptKey);
			json = WebUtil.bulidSuccessJson("{\"worksId\":" + worksId + "}");
			out.write(json);
		} catch (AppException e) {
			log.error("createWorks", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("createWorks", e);
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
	 * 获取用户工作集List
	 * @param response
	 * @param memberId 用户Id
	 * @param encryptKey
	 * @param isOnlyDesk 是否为桌面  1为桌面，为最近使用
	 * @param isOrderLastDay 是否为按照lastUpdate倒叙
	 */
	@ResponseBody
	@RequestMapping(value = "/loadWorksList")
	public void loadWorksList(HttpServletResponse response, Integer memberId, String encryptKey, Integer isOnlyDesk,
			Integer isOrderLastDay) {
		if (log.isDebugEnabled()) {
			log.debug("loadWorksList-->memberId:{},encryptKey:{},isOnlyDesk:{},isOrderLastDay:{}", memberId, encryptKey,
					isOnlyDesk, isOrderLastDay);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsWorksDto> worksDtoPage = this.atalasAdminService.loadWorksList(memberId, encryptKey, isOnlyDesk,
					isOrderLastDay);
			json = WebUtil.bulidSuccessJson(worksDtoPage);
			out.write(json);
		} catch (AppException e) {
			log.error("loadWorksList", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadWorksList", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}

	}

	@RequestMapping(value = "/loadAllMaterial")
	@ResponseBody
	public void loadAllMaterial(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("loadAllMaterial");
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsMaterial> materialList = this.atalasAdminService.queryAllMaterial();
			json = WebUtil.bulidSuccessJson(materialList);
			out.write(json);
		} catch(AppException e) {
			log.error("loadAllMaterial", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch(Exception e) {
			log.error("loadAllMaterial", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}		
	}
	
	private Map<String, Object> getArtworkInfo(String imageUrl, String artistName, Integer artistId, String artworkName,
			Integer materialId, Integer length, Integer hight, Integer depth, Double price, Integer createYear,
			String material, String dimension) {
		Map<String, Object> artworkInfo = new HashMap<String, Object>();
		if (StringUtil.isNullString(imageUrl)) {
			throw new AppException(AppException.BusiAppCode.NULL_PARAM);
		}
		if (StringUtil.isNullString(artworkName)) {
			throw new AppException(AppException.BusiAppCode.NULL_PARAM, "作品名称不能为空");
		} else {
			if (artworkName.length() >= 30) {
				throw new AppException(AppException.BusiAppCode.NULL_PARAM, "作品名称长度不能超过30个字");
			}
		}
		if (StringUtil.isNullString(artistName)) {
			throw new AppException(AppException.BusiAppCode.NULL_PARAM, "作者名称不能为空");
		} else {
			if (artistName.length() >= 10) {
				throw new AppException(AppException.BusiAppCode.NULL_PARAM, "作者名称长度不能超过10个字");
			}
			artistId = 0;
		}
		if (StringUtil.isNullString(material)) {
			throw new AppException(AppException.BusiAppCode.NULL_PARAM, "材质不能为空");
		} else {
			if (material.length() >= 20) {
				throw new AppException(AppException.BusiAppCode.NULL_PARAM, "材质长度不能超过20个字");
			}
		}
		if (StringUtil.isNullString(dimension)) {
			throw new AppException(AppException.BusiAppCode.NULL_PARAM, "尺寸不能为空");
		} else {
			if (dimension.length() >= 20) {
				throw new AppException(AppException.BusiAppCode.NULL_PARAM, "尺寸长度不能超过20个字");
			}
		}
		if (price == null || price < 0) {
			throw new AppException(AppException.BusiAppCode.NULL_PARAM, "作品价格有误");
		}
		artworkInfo.put("imageUrl", imageUrl);
		artworkInfo.put("artistId", artistId);
		artworkInfo.put("artistName", artistName);
		artworkInfo.put("artworkName", artworkName);
		artworkInfo.put("materialId", materialId);
		artworkInfo.put("length", length);
		artworkInfo.put("hight", hight);
		artworkInfo.put("depth", depth);
		int priceTemp = CurrencyUtil.mul(CurrencyUtil.div(price, 1, 2), 100).intValue();
		artworkInfo.put("price", priceTemp);
		artworkInfo.put("createYear", createYear);
		artworkInfo.put("material", material);
		artworkInfo.put("dimension", dimension);
		return artworkInfo;
	}
}
