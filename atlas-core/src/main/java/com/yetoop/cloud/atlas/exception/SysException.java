package com.yetoop.cloud.atlas.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SysException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 945250839333583447L;
	private String code;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public SysException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.msg = msg;
	}

	public String getStackTraceMsg() {
		ByteArrayOutputStream os = null;
		PrintStream ps = null;
		try {
			os = new ByteArrayOutputStream();
			ps = new PrintStream(os);
			printStackTrace(ps);
			return os.toString();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					System.err.println("应用异常堆栈信息输出流关闭失败。。");
				}
			}
		}

	}

}
