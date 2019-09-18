package com.yetoop.cloud.atlas.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yetoop.cloud.atlas.domain.EsMember;
import com.yetoop.cloud.atlas.dto.AsWorksDto;
import com.yetoop.cloud.atlas.dto.EsMemberDto;

public interface AtlasUserService {
	
	/**
	 * 获取微信config
	 * @param url
	 * @return
	 */
	public Map<String, String> initWeixinConfig(String url);
	
	/**
	 * 微信登录获取用户信息，若未登录则注册后登录
	 * @param request
	 * @param code
	 * @param state
	 * @return
	 */
	public EsMemberDto wxLoginCallBack(HttpServletRequest request, String code, String state);
	
	/**
	 * 统一登录接口
	 * @param member
	 * @return
	 */
	public EsMemberDto memberLogin(EsMember member);
	
	/**
	 * wap微信授权登录
	 * @param session
	 * @param request
	 * @return
	 */
	public Map<String, Object> wxLogin(HttpSession session, HttpServletRequest request);
	
	/**
	 * 获取作品列表
	 * @param memberId
	 * @param encryptKey
	 * @param priviewCode 预览码
	 * @return
	 */
	public AsWorksDto queryArtworksByPriviewCode(Integer memberId, String encryptKey, String priviewCode);
	
	/**
	 * 获取作品列表
	 * @param memberId
	 * @param encryptKey
	 * @param viewCode 公开链接码
	 * @return
	 */
	public AsWorksDto queryArtworksByViewCode(Integer memberId, String encryptKey, String viewCode);
	
	/**
	 *  查询作品集信息
	 * @param memberId
	 * @param encryptKey
	 * @param priviewCode 预览码
	 * @return
	 */
	public AsWorksDto queryWorksDetailByPriviewCode(Integer memberId, String encryptKey, String priviewCode);
	
	/**
	 *  查询作品集信息
	 * @param memberId
	 * @param encryptKey
	 * @param viewCode 公开链接码
	 * @return
	 */
	public AsWorksDto queryWorksDetailByViewCode(Integer memberId, String encryptKey, String viewCode);
	
	
	/**
	 * 手机号码是否存在
	 * @param phone
	 * @return
	 */
	public boolean isMemberPhoneExist(String phone);

}
