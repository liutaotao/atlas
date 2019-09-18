package com.yetoop.cloud.atlas.common;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QrUtil {
	public static final String DEFAULT_QR_DIR = "../pic/qr/";
	private static String PICPATH = null;

	public static String getPicPath(){
		if(PICPATH == null){
			PICPATH = QrUtil.class.getClassLoader().getResource("/").getPath()+"../";
		}
		return PICPATH;
	}
	
	
	public static String genarateQr(String url) {
		String imageUrl = null;
		try {
			
			String path =  DEFAULT_QR_DIR + DateUtil.formatDate(new Date(), "yyyyMMdd")
					+ "/";

			File filePath = new File(getPicPath() + path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String fileName = url.hashCode() + ".jpg";
			File file1 = new File(getPicPath() + path, fileName);
			if (!file1.exists()) {
				MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
				Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
				file1.createNewFile();
				MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
			}
			imageUrl = path + fileName;
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return imageUrl;

	}
}
