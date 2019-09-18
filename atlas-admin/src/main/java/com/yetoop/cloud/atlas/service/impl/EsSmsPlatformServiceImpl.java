package com.yetoop.cloud.atlas.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.domain.EsSmsPlatform;
import com.yetoop.cloud.atlas.domain.persistence.EsSmsPlatformMapper;
import com.yetoop.cloud.atlas.service.EsSmsPlatformService;
import com.yetoop.cloud.atlas.service.SmsMessageService;

import net.sf.json.JSONObject;

@Service("esSmsPlatformService")
public class EsSmsPlatformServiceImpl implements EsSmsPlatformService {
	
	private static final Logger log = LoggerFactory.getLogger(EsSmsPlatformServiceImpl.class);
	
	@Autowired
	private EsSmsPlatformMapper smsPlatformMapper;
	
	@Autowired
	private SmsMessageService smsMessageService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void send(String phone, String content,Map param) {
		if (log.isDebugEnabled()) {
			log.debug("send-->phone:{},content:{},param:{}", phone, content, param);
		}
		try {
			EsSmsPlatform platform = this.smsPlatformMapper.queryOpenSmsPlatform();

			// 判断是否设置了短信网关组件 add 2016-3-17
			if (platform != null) {
				String config = platform.getConfig();
				JSONObject jsonObject = JSONObject.fromObject(config);
				String code = "";
				if (param != null) {
					code = (String) param.get("code");
				}
				String templateId = smsMessageService.getTemplateId("memberRegister");
				Map<String, String> itemMap = (Map<String, String>) jsonObject.toBean(jsonObject, Map.class);
				itemMap.put("code", code);
				String mins = "10";
				smsMessageService.onSend(phone, templateId, new String[] { content, mins });
			} else {
				throw new RuntimeException("你可能还未安装任何短信网关组件或配置错误！");
			}

		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
