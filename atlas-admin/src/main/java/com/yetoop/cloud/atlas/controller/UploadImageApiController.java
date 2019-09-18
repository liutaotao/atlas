package com.yetoop.cloud.atlas.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yetoop.cloud.atlas.common.FileUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.upload.IUploader;
import com.yetoop.cloud.atlas.upload.UploadFacatory;
import com.yetoop.cloud.atlas.utils.WebUtil;

/**
 * 图片上传API
 * 
 * @author zhenyi 2015-9-22 version 1.1 添加注释
 */
@Controller
@Scope("prototype")
@RequestMapping("/upload-image")
public class UploadImageApiController {

	private static final Logger log = LoggerFactory.getLogger(UploadImageApiController.class);

	/**
	 * 上传图片
	 * 
	 * @param image
	 *            图片
	 * @param imageFileName
	 *            图片名称
	 * @param subFolder
	 *            存放文件夹名称
	 * @return 上传成功返回： 图片地址，失败返回上传图片错误信息
	 */
	@ResponseBody
	@RequestMapping(value = "/upload-image")
	public void uploadImage(HttpServletResponse response, MultipartFile file, String subFolder) {
		if (log.isDebugEnabled()) {
			log.debug("uploadImage-->file:{},encryptKey:{}", file, subFolder);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			if (file != null) {
				if (!FileUtil.isAllowUpImg(file.getOriginalFilename())) {
					throw new AppException(AppException.FileImageAppCode.NOT_FILE_IMAGE_TYPE);
				} else {
					InputStream stream = null;
					stream = file.getInputStream();
					IUploader uploader = UploadFacatory.getUploaer();
					String fsImgPath = uploader.upload(stream, subFolder, file.getOriginalFilename());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("img", fsImgPath);
					json = WebUtil.bulidSuccessJson(map);
				}
			} else {
				throw new AppException(AppException.FileImageAppCode.NULL_FILE_IMAGE);
			}

			out.write(json);
		} catch (AppException e) {
			log.error("uploadImage", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("uploadImage", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param image
	 *            图片
	 * @param imageFileName
	 *            图片名称
	 * @param subFolder
	 *            存放文件夹名称
	 * @return 上传成功返回： 图片地址，失败返回上传图片错误信息
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public void upload(HttpServletResponse response, MultipartFile image, String subFolder) {
		if (log.isDebugEnabled()) {
			log.debug("upload-->image:{},encryptKey:{}", image, subFolder);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			if (image != null) {
				if (!FileUtil.isAllowUpImg(image.getOriginalFilename())) {
					throw new AppException(AppException.FileImageAppCode.NOT_FILE_IMAGE_TYPE);
				} else {
					InputStream stream = null;
					try {
						stream = image.getInputStream();
					} catch (Exception e) {
						e.printStackTrace();
					}
					IUploader uploader = UploadFacatory.getUploaer();
					String fsImgPath = uploader.upload(stream, subFolder, image.getOriginalFilename());
					json = WebUtil.bulidSuccessJson("{\"img\":\"" + fsImgPath + "\"}");
				}
			} else {
				throw new AppException(AppException.FileImageAppCode.NULL_FILE_IMAGE);
			}

			out.write(json);
		} catch (AppException e) {
			log.error("upload", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("upload", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

	/**
	 * 上传base64图片
	 * 
	 * @param base64Len
	 *            长度
	 * @param base64
	 *            字节码
	 * @param type
	 *            类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload-base64")
	public void uploadBase64(HttpServletResponse response, String base64, String type) {
		if (log.isDebugEnabled()) {
			log.debug("uploadBase64-->base64:{},subFolder:{}", base64, type);
		}
		String json = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			out = response.getWriter();
			if(StringUtil.isNullString(base64)){
				throw new AppException(AppException.FileImageAppCode.NULL_FILE_IMAGE);
			}
			IUploader uploader = UploadFacatory.getUploaer();
			Map<String, Object> map = new HashMap<String, Object>();
			
			String path;
			String subFolder = type;
			path = uploader.uploadBase64(subFolder, base64);
			map.put("img", path);
			json = WebUtil.bulidSuccessJson(map);
			out.write(json);
		} catch (AppException e) {
			log.error("upload", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} catch (Exception e) {
			log.error("upload", e);
			json = WebUtil.buildFailJson(e);
			out.write(json);
		} finally {
			if (out != null) {
				out.close();
				out.flush();
			}
		}
	}

}
