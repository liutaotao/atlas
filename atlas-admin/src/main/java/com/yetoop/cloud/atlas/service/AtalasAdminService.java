package com.yetoop.cloud.atlas.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsGallary;
import com.yetoop.cloud.atlas.domain.AsMaterial;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.dto.AsArtworkDto;
import com.yetoop.cloud.atlas.dto.AsWorksDto;
import com.yetoop.cloud.atlas.dto.EsMemberDto;
import com.zhenyi.common.pager.PagedList;

public interface AtalasAdminService {
	
	/**
	 * 修改画廊名称
	 * @param memberId
	 * @param encryptKey
	 * @param gallaryName
	 */
	public void modifyGallaryName(Integer memberId, String encryptKey, String gallaryName);
	
	/**
	 * 恢复作品集
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	public void restoreWorks(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 彻底删除作品集
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	public void deleteWorks(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 创建作品集副本
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	public void createReplicaWorks(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 获取pc用户绑定微信配置信息
	 * @return
	 */
	public Map<String, Object> initWxPcBindConfig(HttpSession session);
	
	/**
	 * 获取pc微信登录配置信息
	 * @return
	 */
	public Map<String, Object> initWxPcLoginConfig(String wxConfigType, HttpSession session);
	
	/**
	 * 查询回收站作品集列表并按seq倒序
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public List<AsWorksDto> queryTrashWorks(Integer memberId, String encryptKey);
	
	/**
	 * 修改作品集名称并更改作品集操作时间
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param worksName 作品集名称
	 * @return
	 */
	public AsWorks modifyWorksName(Integer memberId, String encryptKey, Integer worksId, String worksName);
	
	/**
	 * 根据作品集Id获取该作品集所有的作品
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @return
	 */
	public List<AsArtworkDto> loadAllArtworkDto(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 获取作品集中的作品
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @return
	 */
	public AsArtwork queryArtwork(Integer memberId, String encryptKey, Integer worksId, Integer artworkId);

	/**
	 * 根据作品集Id获取管理员管理的作品集
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @return
	 */
	public AsWorks queryWorks(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 查询所有的材质
	 * @return
	 */
	public List<AsMaterial> queryAllMaterial();
	
	/**
	 * 管理员用户创建未命名作品集并返回作品集Id
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public Integer createWorks(Integer memberId, String encryptKey);

	/**
	 * 获取用户的管理的作品集List
	 * @param memberId
	 * @param encryptKey
	 * @param isOnlyDesk
	 * @param isOrderLastDay
	 * @return
	 */
	public List<AsWorksDto> loadWorksList(Integer memberId, String encryptKey, Integer isOnlyDesk,
			Integer isOrderLastDay);
	
	/**
	 * 根据画廊编号获取作品集
	 * @param adminMemberId
	 * @param gallerId
	 * @param onlyDesk 是否为桌面
	 * @param orderLastDay 是否按修改时间排序
	 * @return
	 */
	public List<AsWorksDto> queryWorks(Integer adminMemberId, Integer galleryId, boolean onlyDesk, boolean orderLastDay);
	
	/**
	 *  创建作品集
	 * @param adminMemberId 管理员Id
	 * @param galleryId 画廊Id
	 * @param worksName 作品集名称
	 */
	public Integer createWorks(Integer adminMemberId, Integer galleryId, String worksName);

	/**
	 *  删除该作品集(将作品集修改为废弃状态)
	 * @param adminMemberId
	 * @param encryptKey
	 * @param worksId
	 */
	public void removeWorks(Integer memberId,String encryptKey, Integer worksId);

	/**
	 * 修改作品集权限
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param permissionCode private:私有  link:公开链接 
	 * @return 
	 */
	public AsWorks modifyWorksViewPermission(Integer memberId, String encryptKey, Integer worksId, String permissionCode);

	/**
	 * 生成临时预览连接 
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 */
	public String modifyWorks4Priview(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 查询用户管理的画廊
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public AsGallary queryMemberGallery(Integer memberId, String encryptKey);
	
	/**
	 * 查询作品集下的所有作品List
	 * @param memberId
	 * @param worksId
	 * @return
	 */
	public List<AsArtworkDto> queryAllArtworks(Integer memberId, Integer worksId);

	/**
	 * 查询作品集下的作品List
	 * @param memberId 
	 * @param worksId 作品集Id
	 * @param pageParam 分页参数(pageSize,currentPage)
	 * @return
	 */
	public PagedList<AsArtworkDto> queryArtworks(Integer memberId, Integer worksId, Map<String, String> pageParam);

	/**
	 * 创建作品集中的作品
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkInfo
	 */
	public Integer createArtwork(Integer memberId, String encryptKey, Integer worksId,
			Map<String, Object> artworkInfo);

	/**
	 * 修改作品
	 * @param adminMemberId
	 * @param galleryId
	 * @param worksId
	 * @param artworkInfo
	 */
	public void modifyArtwork(Integer adminMemberId, Integer galleryId, Integer worksId,
			Map<String, Object> artworkInfo);

	/**
	 * 修改作品集作品顺序
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkIds
	 */
	public void modifyArtworkSeq(Integer memberId,String encryptKey,Integer worksId,Integer[] artworkIds);

	/**
	 * 删除作品
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 */
	public void removeArtwork(Integer memberId, String encryptKey, Integer worksId,Integer artworkId);

	/**
	 * 查询用户信息
	 * @param memberId
	 * @param encryptKey
	 * @return
	 */
	public EsMemberDto queryMemberDto(Integer memberId, String encryptKey);
	
	public void saveFeedBack(Integer memberId,String encryptKey,String detail);

}
