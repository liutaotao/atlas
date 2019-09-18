package com.yetoop.cloud.atlas.service;

import java.util.Map;

import com.yetoop.cloud.atlas.domain.AsConfig;

public interface AtlasConfigService {
	
	/**
	 * 通过code获取配置
	 * @param code
	 * @return
	 */
	public AsConfig getAsConfig(String code);
	
	/**
	 * 获取配置参数值
	 * @param code
	 * @return
	 */
	public String getConfigValue(String code);
	
	/**
	 * 获取配置参数的Map
	 * @param code
	 * @return
	 */
	public Map<String, Object> getConfigValueMap(String code);

}
