package com.yetoop.cloud.atlas.service.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsConfig;
import com.yetoop.cloud.atlas.domain.persistence.AsConfigMapper;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtlasConfigService;

@Component
public class AtlasConfigServiceImpl implements AtlasConfigService {

	private static final Logger log = LoggerFactory.getLogger(AtlasConfigServiceImpl.class);

	@Autowired
	private AsConfigMapper asConfigMapper;

	@Override
	public AsConfig getAsConfig(String code) {
		if (log.isDebugEnabled()) {
			log.debug("getAsConfig-->code:{}", code);
		}
		if (code == null || code == "") {
			throw new AppException(AppException.ConfigAppCode.NULL_CONFIG_CODE);
		}
		AsConfig asConfig = asConfigMapper.selectByCode(code);
		if (log.isDebugEnabled()) {
			log.debug("getAsConfig-->asConfig:{}", asConfig);
		}
		if (asConfig == null) {
			throw new AppException(AppException.ConfigAppCode.NULL_CONFIG);
		}
		return asConfig;
	}

	@Override
	public String getConfigValue(String code) {
		AsConfig asConfig = this.getAsConfig(code);
		String configValue = asConfig.getConfigValue();
		if (log.isDebugEnabled()) {
			log.debug("getConfigValue-->configValue:{}", configValue);
		}
		return configValue;
	}

	@Override
	public Map<String, Object> getConfigValueMap(String code) {
		AsConfig asConfig = this.getAsConfig(code);

		Map<Object, Object> asCofigMap = new BeanMap(asConfig);
		if (log.isDebugEnabled()) {
			log.debug("getConfigValueMap-->asCofigMap:{}", asCofigMap);
		}
		Map<String, Object> configValueMap = null;
		try {
			// JsonUtil.toMap(StringUtil.toString(asCofigMap.get("configValue")));
			configValueMap = JSON.parseObject(StringUtil.toString(asCofigMap.get("configValue")), Map.class);
		} catch (Exception e) {
			throw new AppException(AppException.ConfigAppCode.INVALID_CONFIG);
		}
		if (log.isDebugEnabled()) {
			log.debug("getConfigValueMap-->configValueMap:{}", configValueMap);
		}
		if (configValueMap == null) {
			throw new AppException(AppException.ConfigAppCode.INVALID_CONFIG);
		}
		return configValueMap;
	}

}
