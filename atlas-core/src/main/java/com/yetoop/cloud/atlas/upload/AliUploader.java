package com.yetoop.cloud.atlas.upload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.FileUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.service.AtlasConfigService;

@Component
public class AliUploader implements IUploader {
	

	private OSSClient client;

	private String bucket;
	
	private String endPointKey;

	@Autowired
	private AtlasConfigService atlasConfigService;
	
	
	@Override
	public String uploadBase64( String subFolder, String imageString) {
		init();
		String[] sArray = imageString.split(";base64,");
		String imageType = sArray[0];
		imageString = sArray[1];
		imageType = imageType.split("/")[1];

 

		BASE64Decoder decoder = new BASE64Decoder();

		byte[] b = null;
		try {
			//Base64解码
			b = decoder.decodeBuffer(imageString);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {//调整异常数据
					b[i] += 256;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + ".jpeg";
		return this.upload(new ByteArrayInputStream(b), subFolder, fileName);
	}


	@Override
	public String upload(InputStream stream, String subFolder, String fileName) {
		init();
		/**
		 * 参数校验
		 */
		if(stream==null){
			throw new IllegalArgumentException("file or filename object is null");
		}
		if(StringUtil.isNullString(subFolder)){
			throw new IllegalArgumentException("subFolder is null");
		}
		if(!FileUtil.isAllowUpImg(fileName)){
			throw new IllegalArgumentException("不被允许的上传文件类型");
		}
		String ext = FileUtil.getFileExt(fileName);
		fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;
		

		//key 不以“/”开头
		if(!subFolder.startsWith("/")){
			subFolder = "/"+subFolder;
		}
		if(!fileName.startsWith("/")){
			fileName = "/"+fileName;
		}
		String key =  this.bucket+"/atlas"+subFolder+fileName;
		this.client.putObject(bucket, key, stream);
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = "https://"+bucket+"."+endPointKey+"/"+key;
		return url;
	}

	@Override
	public void deleteFile(String filePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] upload(InputStream stream, String subFolder, String fileName, int width, int height) {
		String [] path = new String[2];
		path[0]  = this.upload(stream, subFolder, fileName);
		path[1] = path[0] + "?x-oss-process=image/resize,w_+"+width+",h_"+height
				+",m_fill/auto-orient,1/quality,q_90/format,src";

		return path;
	}

	private void init() {
		if (client == null) {
			Map<String, Object> map = atlasConfigService.getConfigValueMap("oss_config");
			this.endPointKey = StringUtil.toString(map.get("END_POINT"));
			String accessId = StringUtil.toString(map.get("ACCESS_ID"));
			String accessKey = StringUtil.toString(map.get("ACCESS_KEY"));
			this.bucket = StringUtil.toString(map.get("BUCKET"));

			client = new OSSClient(endPointKey, accessId, accessKey);

		}

	}


}
