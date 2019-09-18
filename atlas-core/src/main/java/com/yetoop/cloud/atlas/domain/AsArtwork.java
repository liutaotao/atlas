package com.yetoop.cloud.atlas.domain;

import java.util.Date;
import java.util.Map;

import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;
import com.yetoop.cloud.atlas.exception.AppException;

public class AsArtwork {
	
	public void copyArtwork(AsArtwork artwork) {
		Date now = new Date();
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.createDate = now;
		this.version = now;
		this.intro = artwork.getIntro();
		this.name = artwork.getName();
		this.seq = artwork.getSeq();
		this.ownMemberId = artwork.getOwnMemberId();
		this.imageSize = artwork.getImageSize();
		this.bigImageUrl = artwork.getBigImageUrl();
		this.bigImageLength = artwork.getBigImageLength();
		this.bigImageHeight = artwork.getBigImageHeight();
		this.bigImageSize = artwork.getBigImageSize();
		this.smallImageUrl = artwork.getSmallImageUrl();
		this.smallImageLength = artwork.getSmallImageLength();
		this.smallImageHeight = artwork.getSmallImageHeight();
		this.smallImageSize = artwork.getSmallImageSize();
		this.imageUrl = artwork.getImageUrl();
		this.imageLength = artwork.getImageLength();
		this.imageHeight = artwork.getImageHeight();
		this.artistName = artwork.getArtistName();
		this.artistId = artwork.getArtistId();
		this.createYear = artwork.getCreateYear();
		this.price = artwork.getPrice();
		this.length = artwork.getLength();
		this.hight = artwork.getHight();
		this.depth = artwork.getDepth();
		this.materialId = artwork.getMaterialId();
		this.subjectId = artwork.getSubjectId();
		this.material = artwork.getMaterial();
		this.dimension = artwork.getDimension();
	}

	public void updateArtworkSeq(Integer seq) {
		Date now = new Date();
		this.seq = seq;
		this.version = now;
	}

	public void delete(Integer memberId) {
		if (memberId == null || this.ownMemberId == null || this.ownMemberId.intValue() != memberId.intValue()) {
			throw new AppException(AppException.BusiAppCode.NO_AUTHORITY_MODIFY_ARTWORK, "无权删除此作品");
		}
		Date now = new Date();
		this.state = StateEnum.SCRAP.getState();
		this.stateDate = now;
		this.version = now;
	}

	public void create(Map<String, Object> artworkInfo, Integer adminMemberId, int maxSeq) {
		Date now = new Date();
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.createDate = now;
		this.version = now;
		this.intro = StringUtil.toString(artworkInfo.get("intro"));
		this.name = StringUtil.toString(artworkInfo.get("artworkName"));
		this.seq = (maxSeq + 1);
		this.ownMemberId = adminMemberId;
		this.imageSize = StringUtil.toInt(artworkInfo.get("imageSize"), 0, false);
		this.bigImageUrl = StringUtil.toString(artworkInfo.get("bigImageUrl"));
		this.bigImageLength = StringUtil.toInt(artworkInfo.get("bigImageLength"), 0, false);
		this.bigImageHeight = StringUtil.toInt(artworkInfo.get("bigImageHeight"), 0, false);
		this.bigImageSize = StringUtil.toInt(artworkInfo.get("bigImageSize"), 0, false);
		this.smallImageUrl = StringUtil.toString(artworkInfo.get("smallImageUrl"));
		this.smallImageLength = StringUtil.toInt(artworkInfo.get("smallImageLength"), 0, false);
		this.smallImageHeight = StringUtil.toInt(artworkInfo.get("smallImageHeight"), 0, false);
		this.smallImageSize = StringUtil.toInt(artworkInfo.get("smallImageSize"), 0, false);
		this.imageUrl = StringUtil.toString(artworkInfo.get("imageUrl"));
		this.imageLength = StringUtil.toInt(artworkInfo.get("imageLength"), 0, false);
		this.imageHeight = StringUtil.toInt(artworkInfo.get("imageHeight"), 0, false);
		this.artistName = StringUtil.toString(artworkInfo.get("artistName"));
		this.artistId = StringUtil.toInt(artworkInfo.get("artistId"), 0, false);
		int year = StringUtil.toInt(artworkInfo.get("createYear"), 0, false);
		if (year != 0) {
			this.createYear = year;
		}
		this.price = StringUtil.toInt(artworkInfo.get("price"), 0, false);
		this.length = StringUtil.toInt(artworkInfo.get("length"), 0, false);
		this.hight = StringUtil.toInt(artworkInfo.get("hight"), 0, false);
		this.depth = StringUtil.toInt(artworkInfo.get("depth"), 0, false);
		this.materialId = StringUtil.toInt(artworkInfo.get("materialId"), 0, false);
		this.subjectId = StringUtil.toInt(artworkInfo.get("subjectId"), 0, false);
		this.material = StringUtil.toString(artworkInfo.get("material"), false);
		this.dimension = StringUtil.toString(artworkInfo.get("dimension"), false);
	}

	private AsArtist artist;

	private AsMaterial asMaterial;

	private Integer id;

	private String intro;

	private String name;

	private Integer seq;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer ownMemberId;

	private Integer imageSize;

	private String bigImageUrl;

	private Integer bigImageLength;

	private Integer bigImageHeight;

	private Integer bigImageSize;

	private String smallImageUrl;

	private Integer smallImageLength;

	private Integer smallImageHeight;

	private Integer smallImageSize;

	private String imageUrl;

	private Integer imageLength;

	private Integer imageHeight;

	private String artistName;

	private Integer artistId;

	private Integer createYear;

	private Integer price;

	private Integer length;

	private Integer hight;

	private Integer depth;

	private Integer materialId;

	private Integer subjectId;

	private String material;

	private String dimension;

	public AsArtist getArtist() {
		return artist;
	}

	public void setArtist(AsArtist artist) {
		this.artist = artist;
	}

	public AsMaterial getAsMaterial() {
		return asMaterial;
	}

	public void setAsMaterial(AsMaterial asMaterial) {
		this.asMaterial = asMaterial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Integer getOwnMemberId() {
		return ownMemberId;
	}

	public void setOwnMemberId(Integer ownMemberId) {
		this.ownMemberId = ownMemberId;
	}

	public Integer getImageSize() {
		return imageSize;
	}

	public void setImageSize(Integer imageSize) {
		this.imageSize = imageSize;
	}

	public String getBigImageUrl() {
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}

	public Integer getBigImageLength() {
		return bigImageLength;
	}

	public void setBigImageLength(Integer bigImageLength) {
		this.bigImageLength = bigImageLength;
	}

	public Integer getBigImageHeight() {
		return bigImageHeight;
	}

	public void setBigImageHeight(Integer bigImageHeight) {
		this.bigImageHeight = bigImageHeight;
	}

	public Integer getBigImageSize() {
		return bigImageSize;
	}

	public void setBigImageSize(Integer bigImageSize) {
		this.bigImageSize = bigImageSize;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public Integer getSmallImageLength() {
		return smallImageLength;
	}

	public void setSmallImageLength(Integer smallImageLength) {
		this.smallImageLength = smallImageLength;
	}

	public Integer getSmallImageHeight() {
		return smallImageHeight;
	}

	public void setSmallImageHeight(Integer smallImageHeight) {
		this.smallImageHeight = smallImageHeight;
	}

	public Integer getSmallImageSize() {
		return smallImageSize;
	}

	public void setSmallImageSize(Integer smallImageSize) {
		this.smallImageSize = smallImageSize;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getImageLength() {
		return imageLength;
	}

	public void setImageLength(Integer imageLength) {
		this.imageLength = imageLength;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public Integer getArtistId() {
		return artistId;
	}

	public void setArtistId(Integer artistId) {
		this.artistId = artistId;
	}

	public Integer getCreateYear() {
		return createYear;
	}

	public void setCreateYear(Integer createYear) {
		this.createYear = createYear;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getHight() {
		return hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	@Override
	public String toString() {
		return "AsArtwork [artist=" + artist + ", asMaterial=" + asMaterial + ", id=" + id + ", intro=" + intro
				+ ", name=" + name + ", seq=" + seq + ", createDate=" + createDate + ", version=" + version + ", state="
				+ state + ", stateDate=" + stateDate + ", ownMemberId=" + ownMemberId + ", imageSize=" + imageSize
				+ ", bigImageUrl=" + bigImageUrl + ", bigImageLength=" + bigImageLength + ", bigImageHeight="
				+ bigImageHeight + ", bigImageSize=" + bigImageSize + ", smallImageUrl=" + smallImageUrl
				+ ", smallImageLength=" + smallImageLength + ", smallImageHeight=" + smallImageHeight
				+ ", smallImageSize=" + smallImageSize + ", imageUrl=" + imageUrl + ", imageLength=" + imageLength
				+ ", imageHeight=" + imageHeight + ", artistName=" + artistName + ", artistId=" + artistId
				+ ", createYear=" + createYear + ", price=" + price + ", length=" + length + ", hight=" + hight
				+ ", depth=" + depth + ", materialId=" + materialId + ", subjectId=" + subjectId + ", material="
				+ material + ", dimension=" + dimension + "]";
	}

}