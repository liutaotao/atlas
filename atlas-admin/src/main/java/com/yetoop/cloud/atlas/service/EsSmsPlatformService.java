package com.yetoop.cloud.atlas.service;

import java.util.Map;

public interface EsSmsPlatformService {

	public void send(String phone, String content, Map<String, Object> param);

}
