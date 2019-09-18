package com.yetoop.cloud.atlas.upload;

import org.springframework.stereotype.Component;

import com.yetoop.cloud.atlas.common.SpringContextHolder;
@Component
public class UploadFacatory {

	private UploadFacatory(){}
	
	
	/**
	 * 上传图片
	 * @return
	 */
	public static IUploader getUploaer(){
//		IUploader uploade =(IUploader)SpringContextHolder.getBean("localUploader");
		//AliUploader
		IUploader uploade =(IUploader)SpringContextHolder.getBean("aliUploader");
		return uploade;
	}


}
