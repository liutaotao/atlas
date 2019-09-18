package com.yetoop.cloud.atlas.domain;

import java.math.BigDecimal;

import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;

public class EsMember {
	
	public void updateMemberFace(String face) {
		this.face = face;
	}
	
	public void wxBind(String wxUnionid, String wxOpenid) {
		this.wxUnionid = wxUnionid;
		this.wxOpenid = wxOpenid;
	}
	
	public void loosenWxBind(){
		this.wxOpenid = "";
		this.wxUnionid = "";
	}
	
	public void updatePhone(String phone) {
		this.mobile = phone;
	}
	
	public void updatePassword(String password){
		this.password = StringUtil.md5(password);
	}
	
	public void create(String phone, String password) {
		long now = DateUtil.getDateline();
		this.name = phone;
		this.uname = phone;
		this.nickname = phone;
		this.mobile = phone;
		this.tel = phone;
		if(StringUtil.isNullString(password)){
			this.password = "";
		}else{
			this.password = StringUtil.md5(password);
		}
		this.encryptKey = StringUtil.randomString(32);
		this.disabled = 0;
		this.lastlogin = now;
		this.regtime = now;
		this.lvId = 0;
		this.point = 0;
		this.advance = BigDecimal.valueOf(0d);
		this.logincount = 0;
		this.mp = 0;
		this.face = "";
		this.midentity = 0;
		this.sex = 1;
	}

	public boolean isScrapMember() {
		if (this.disabled == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "EsMember [memberId=" + memberId + ", agentid=" + agentid + ", parentid=" + parentid + ", lvId=" + lvId
				+ ", uname=" + uname + ", email=" + email + ", password=" + password + ", regtime=" + regtime
				+ ", pwAnswer=" + pwAnswer + ", pwQuestion=" + pwQuestion + ", name=" + name + ", sex=" + sex
				+ ", birthday=" + birthday + ", advance=" + advance + ", provinceId=" + provinceId + ", cityId="
				+ cityId + ", regionId=" + regionId + ", townId=" + townId + ", province=" + province + ", city=" + city
				+ ", region=" + region + ", town=" + town + ", address=" + address + ", zip=" + zip + ", mobile="
				+ mobile + ", tel=" + tel + ", point=" + point + ", mp=" + mp + ", qq=" + qq + ", msn=" + msn
				+ ", lastlogin=" + lastlogin + ", isAgent=" + isAgent + ", logincount=" + logincount + ", isCheked="
				+ isCheked + ", registerip=" + registerip + ", recommendPointState=" + recommendPointState
				+ ", lastSendEmail=" + lastSendEmail + ", infoFull=" + infoFull + ", findCode=" + findCode + ", face="
				+ face + ", nickname=" + nickname + ", midentity=" + midentity + ", disabled=" + disabled + ", storeId="
				+ storeId + ", isStore=" + isStore + ", wxOpenid=" + wxOpenid + ", wxUnionid=" + wxUnionid
				+ ", encryptKey=" + encryptKey + " ]";
	}

	private Integer memberId;

	private Integer agentid;

	private Integer parentid;

	private Integer lvId;

	private String uname;

	private String email;

	private String password;

	private Long regtime;

	private String pwAnswer;

	private String pwQuestion;

	private String name;

	private Integer sex;

	private Long birthday;

	private BigDecimal advance;

	private Integer provinceId;

	private Integer cityId;

	private Integer regionId;

	private Integer townId;

	private String province;

	private String city;

	private String region;

	private String town;

	private String address;

	private String zip;

	private String mobile;

	private String tel;

	private Integer point;

	private Integer mp;

	private String qq;

	private String msn;

	private Long lastlogin;

	private Integer isAgent;

	private Integer logincount;

	private Integer isCheked;

	private String registerip;

	private Integer recommendPointState;

	private Integer lastSendEmail;

	private Integer infoFull;

	private String findCode;

	private String face;

	private String nickname;

	private Integer midentity;

	private Short disabled;

	private Integer storeId;

	private Integer isStore;

	private String wxOpenid;

	private String wxUnionid;

	private String encryptKey;

	private String remark;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getAgentid() {
		return agentid;
	}

	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getLvId() {
		return lvId;
	}

	public void setLvId(Integer lvId) {
		this.lvId = lvId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRegtime() {
		return regtime;
	}

	public void setRegtime(Long regtime) {
		this.regtime = regtime;
	}

	public String getPwAnswer() {
		return pwAnswer;
	}

	public void setPwAnswer(String pwAnswer) {
		this.pwAnswer = pwAnswer;
	}

	public String getPwQuestion() {
		return pwQuestion;
	}

	public void setPwQuestion(String pwQuestion) {
		this.pwQuestion = pwQuestion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getAdvance() {
		return advance;
	}

	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getTownId() {
		return townId;
	}

	public void setTownId(Integer townId) {
		this.townId = townId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getMp() {
		return mp;
	}

	public void setMp(Integer mp) {
		this.mp = mp;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public Long getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Long lastlogin) {
		this.lastlogin = lastlogin;
	}

	public Integer getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(Integer isAgent) {
		this.isAgent = isAgent;
	}

	public Integer getLogincount() {
		return logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	public Integer getIsCheked() {
		return isCheked;
	}

	public void setIsCheked(Integer isCheked) {
		this.isCheked = isCheked;
	}

	public String getRegisterip() {
		return registerip;
	}

	public void setRegisterip(String registerip) {
		this.registerip = registerip;
	}

	public Integer getRecommendPointState() {
		return recommendPointState;
	}

	public void setRecommendPointState(Integer recommendPointState) {
		this.recommendPointState = recommendPointState;
	}

	public Integer getLastSendEmail() {
		return lastSendEmail;
	}

	public void setLastSendEmail(Integer lastSendEmail) {
		this.lastSendEmail = lastSendEmail;
	}

	public Integer getInfoFull() {
		return infoFull;
	}

	public void setInfoFull(Integer infoFull) {
		this.infoFull = infoFull;
	}

	public String getFindCode() {
		return findCode;
	}

	public void setFindCode(String findCode) {
		this.findCode = findCode;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getMidentity() {
		return midentity;
	}

	public void setMidentity(Integer midentity) {
		this.midentity = midentity;
	}

	public Short getDisabled() {
		return disabled;
	}

	public void setDisabled(Short disabled) {
		this.disabled = disabled;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getIsStore() {
		return isStore;
	}

	public void setIsStore(Integer isStore) {
		this.isStore = isStore;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getWxUnionid() {
		return wxUnionid;
	}

	public void setWxUnionid(String wxUnionid) {
		this.wxUnionid = wxUnionid;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}