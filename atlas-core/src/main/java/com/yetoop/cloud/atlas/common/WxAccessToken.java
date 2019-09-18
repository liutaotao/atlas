package com.yetoop.cloud.atlas.common;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

public class WxAccessToken {

	private Date date;

	private String accessToken;

	private Integer expiresIn;

	private String refreshToken;
	
	public boolean isValid() {
		Date now = new Date();
		Date expiredDate = null;
		if (expiresIn != null && expiresIn.intValue() != 0) {
			expiredDate = DateUtils.addSeconds(date, expiresIn);
		}
		return !StringUtil.isNullString(this.accessToken) && expiredDate != null && expiredDate.after(now);
	}
	
	public void create(Integer expiresIn, String accessToken) {
		this.date = new Date();
		this.expiresIn = expiresIn;
		this.accessToken = accessToken;
	}

	public void create(Map<String, Object> tokenMap) {
		this.date = new Date();
		this.accessToken = StringUtil.toString(tokenMap.get("access_token"));
		this.expiresIn = StringUtil.toInt(tokenMap.get("expires_in"), false);
		this.refreshToken = StringUtil.toString(tokenMap.get("refresh_token"));
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "WxAccessToken [date=" + date + ", accessToken=" + accessToken + ", expiresIn=" + expiresIn
				+ ", refreshToken=" + refreshToken + "]";
	}

}
