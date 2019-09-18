package com.yetoop.cloud.atlas.common;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class LoadDataByURL {

	public static String getAllData(String imageUrl) {
		StringBuffer temp = new StringBuffer();
		try {
			String url = imageUrl;
			HttpURLConnection uc = (HttpURLConnection) new URL(url).openConnection();
			uc.setConnectTimeout(10000);
			uc.setDoOutput(true);
			uc.setRequestMethod("GET");
			uc.setUseCaches(false);
			// DataOutputStream out = new
			// DataOutputStream(uc.getOutputStream());

			// 要传的参数
			String s = "";
			// URLEncoder.encode("ra", "GB2312") + "=" +
			// URLEncoder.encode(leibie, "GB2312");
			// s += "&" + URLEncoder.encode("keyword", "GB2312") + "=" +
			// URLEncoder.encode(num, "GB2312");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			// out.writeBytes(s);
			// out.flush();
			// out.close();
			InputStream in = new BufferedInputStream(uc.getInputStream());
			Reader rd = new InputStreamReader(in, "Gb2312");
			int c = 0;
			while ((c = rd.read()) != -1) {
				temp.append((char) c);
			}
			in.close();

		} catch (Exception e) {
			System.out.println("图片没有属性：" + imageUrl);
			return null;
		}
		return temp.toString();
	}

	public static String getRotateNum(String imageUrl) {
		Integer endIndex = imageUrl.indexOf("?");
		if (endIndex != -1) {
			imageUrl = imageUrl.substring(0, endIndex);
		}
		imageUrl += "?x-oss-process=image/info";
		try {
			String imageData = getAllData(imageUrl);
			if (!StringUtil.isNullString(imageData)) {
				// JSONObject imageJson = JSONObject.fromObject(imageData);
				// System.out.println(imageJson.getJSONObject("Orientation"));
				// return imageJson.getJSONObject("Orientation").toString();

				return  JSON.parseObject(imageUrl).getJSONObject("ImageWidth").getString("value") ;
				
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("图片没有旋转属性：" + imageUrl);
			return null;
		}
	}

	public static void main(String[] a) {
		String s = LoadDataByURL.getAllData(
				"https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/mall/im/201705262121317366.jpg?x-oss-process=image/info");
		System.out.println(s);
		JSONObject o =	JSON.parseObject(s);
		String ImageWidth = o.getJSONObject("ImageWidth").getString("value");//(String) ((Map) JsonUtil.toMap(s).get("ImageWidth")).get("value");
		System.out.println(ImageWidth);
	}

}
