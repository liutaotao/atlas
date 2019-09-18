package com.yetoop.cloud.atlas.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yetoop.cloud.atlas.dto.AsIndexArtworkDto;
import com.yetoop.cloud.atlas.dto.AsIndexMemberArtworkDto;
import com.yetoop.cloud.atlas.dto.AsIndexMemberWorksDto;
import com.yetoop.cloud.atlas.dto.AsIndexWorksDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtalasIndexArtworkService;
import com.yetoop.cloud.atlas.service.AtalasIndexWorksService;
import com.yetoop.cloud.atlas.utils.WebUtil;
import com.zhenyi.common.pager.PagedList;

@Controller
@RequestMapping(value="/analysis")
public class AtlasAnalysisController {
	
	private final static Logger log = LoggerFactory.getLogger(AtlasAnalysisController.class);
	
	@Autowired
	private AtalasIndexArtworkService atalasIndexArtworkService;
	@Autowired
	private AtalasIndexWorksService atalasIndexWorksService;
	
	/**
	 * 获取对应用户的概况
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param userId
	 * @param type 0为全部,1为今日,2为昨日,3为最近一周,4为最近一月
	 */
	@ResponseBody
	@RequestMapping(value = "/loadMemberOverview")
	public void loadMemberOverview(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, Integer userId, Integer type) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> map = this.atalasIndexArtworkService.loadMemberOverview(memberId, encryptKey, worksId,
					userId, type);
			json = WebUtil.bulidSuccessJson(map);
			out.write(json);
		} catch (AppException e) {
			log.error("loadMemberOverview", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadMemberOverview", e);
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
	 * 获取对应作品的概况
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @param type 0为全部,1为今日,2为昨日,3为最近一周,4为最近一月
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArtworkOverview")
	public void loadArtworkOverview(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, Integer artworkId, Integer type) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			Map<String, Object> map = this.atalasIndexArtworkService.loadArtworkOverview(memberId, encryptKey, worksId,
					artworkId, type);
			json = WebUtil.bulidSuccessJson(map);
			out.write(json);
		} catch (AppException e) {
			log.error("loadArtworkOverview", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadArtworkOverview", e);
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
	 * 分页获取对应作品的用户喜好分析list
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @param keywords 默认为""
	 * @param currentPageNo 默认为1
	 * @param pageSize 默认为10
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArtworkMemberAnalysisPage")
	public void loadArtworkMemberAnalysisPage(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, Integer artworkId, String keywords, Integer currentPageNo, Integer pageSize) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			PagedList<AsIndexMemberArtworkDto> artworkMemberAnalysisPage = this.atalasIndexArtworkService.loadArtworkMemberAnalysisPage(memberId,
					encryptKey, worksId, artworkId, keywords, currentPageNo, pageSize);
			json = WebUtil.bulidSuccessJson(artworkMemberAnalysisPage);
			out.write(json);
		} catch (AppException e) {
			log.error("loadArtworkMemberAnalysisPage", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadArtworkMemberAnalysisPage", e);
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
	 * 获取全部对应作品的用户喜好分析list
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @param keywords 默认为""
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArtworkMemberAnalysis")
	public void loadArtworkMemberAnalysis(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, Integer artworkId,String keywords) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsIndexMemberArtworkDto> artworkMemberAnalysisList = this.atalasIndexArtworkService
					.loadArtworkMemberAnalysis(memberId, encryptKey, worksId, artworkId, keywords);
			json = WebUtil.bulidSuccessJson(artworkMemberAnalysisList);
			out.write(json);
		} catch (AppException e) {
			log.error("loadArtworkMemberAnalysis", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadArtworkMemberAnalysis", e);
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
	 * 获取对应用户的作品喜好分析list
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value = "/loadMemberArtworkAnalysis")
	public void loadMemberArtworkAnalysis(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, Integer userId) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsIndexMemberArtworkDto> memberArtworkAnalysisList = this.atalasIndexArtworkService
					.loadMemberArtworkAnalysis(memberId, encryptKey, worksId, userId);
			json = WebUtil.bulidSuccessJson(memberArtworkAnalysisList);
			out.write(json);
		} catch (AppException e) {
			log.error("loadMemberArtworkAnalysis", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadMemberArtworkAnalysis", e);
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
	 * 获取全部对应作品集的用户喜好分析list
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param keywords 默认为""
	 */
	@ResponseBody
	@RequestMapping(value = "/loadAllMemberWorksAnalysis")
	public void loadAllMemberWorksAnalysis(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, String keywords) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsIndexMemberWorksDto> allMemberWorksAnalysis = this.atalasIndexWorksService
					.loadAllMemberWorksAnalysis(worksId, memberId, encryptKey, keywords);
			json = WebUtil.bulidSuccessJson(allMemberWorksAnalysis);
			out.write(json);
		} catch (AppException e) {
			log.error("loadAllMemberWorksAnalysis", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadAllMemberWorksAnalysis", e);
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
	 * 分页获取对应作品集的用户喜好分析list
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param keywords 默认为""
	 * @param currentPageNo 默认1
	 * @param pageSize 默认10
	 */
	@ResponseBody
	@RequestMapping(value = "/loadMemberWorksAnalysisPage")
	public void loadMemberWorksAnalysisPage(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId, String keywords, Integer currentPageNo, Integer pageSize) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			PagedList<AsIndexMemberWorksDto> memberWorksAnalysisPage = this.atalasIndexWorksService
					.loadMemberWorksAnalysis(worksId, memberId, encryptKey, keywords, currentPageNo, pageSize);
			json = WebUtil.bulidSuccessJson(memberWorksAnalysisPage);
			out.write(json);
		} catch (AppException e) {
			log.error("loadMemberWorksAnalysisPage", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadMemberWorksAnalysisPage", e);
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
	 * 获取对应作品集的作品喜好分析list
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArtworkAnalysis")
	public void loadArtworkAnalysis(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			List<AsIndexArtworkDto> artworkAnalysisList = this.atalasIndexWorksService.loadArtworkAnalysis(memberId,
					encryptKey, worksId);
			json = WebUtil.bulidSuccessJson(artworkAnalysisList);
			out.write(json);
		} catch (AppException e) {
			log.error("loadArtworkAnalysis", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadArtworkAnalysis", e);
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
	 * 获取作品集概况详情
	 * @param response
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param type 0为全部,1为今日,2为昨日,3为最近一周,4为最近一月
	 */
	@ResponseBody
	@RequestMapping(value = "/loadWorkOverview")
	public void loadWorkOverview(HttpServletResponse response, Integer memberId, String encryptKey,
			Integer worksId,Integer type) {
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			AsIndexWorksDto indexWorksDto = this.atalasIndexWorksService.loadWorkOverview(worksId, memberId, encryptKey, type);
			json = WebUtil.bulidSuccessJson(indexWorksDto);
			out.write(json);
		} catch (AppException e) {
			log.error("loadWorkOverview", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("loadWorkOverview", e);
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
