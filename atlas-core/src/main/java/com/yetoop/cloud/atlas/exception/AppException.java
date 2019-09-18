package com.yetoop.cloud.atlas.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.yetoop.cloud.atlas.common.SpringContextUtil;

public class AppException extends RuntimeException {
	
	public static enum PaymentCode implements AppExceptionCode{
		ERROR_ALI_PAY(7000,"支付宝支付异常"),
		ERROR_ALI_REFUND(7001,"支付宝退款异常"),
		ERROR_ALI_CASH(7002,"支付宝提现异常"),
		ERROR_ALI_SIGN_VERIFY(7003,"支付宝验签失败"),
		ERROR_WX_PAY(7004,"微信支付异常"),
		ERROR_WX_REFUND(7005,"微信退款异常"),
		ERROR_WX_CASH(7006,"微信提现异常"),
		ERROR_WX_SIGN_VERIFY(7007,"支付宝验签失败");
		
		private int code;
		private String errMessage;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMessage() {
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}

		private PaymentCode(int code, String errMessage) {
			this.code = code;
			this.errMessage = errMessage;
		}
	}

	public static enum ConfigAppCode implements AppExceptionCode {
		NULL_CONFIG_CODE(1001,"配置参数不能为空"),
		NULL_CONFIG(1002,"配置数据不存在"),
		MISSING_CONST_CONFIG(1003, "CONST_CONFIG缺少配置数据"), 
		INVALID_NUMBER_CONST_CONFIG(1004, "配置数据不是有效数字"), 
		MISSING_IMAGE_UPLOAD_CONFIG(1005, "IMAGE_UPLOAD_CONFIG缺少配置数据"),
		INVALID_CONFIG(1006,"无效的配置参数");
		private int code;
		private String errMessage;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMessage() {
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}

		private ConfigAppCode(int code, String errMessage) {
			this.code = code;
			this.errMessage = errMessage;
		}
	}

	public static enum BusiAppCode implements AppExceptionCode {
		NULL_ASWORKS_ID(3001, "作品集ID不能为空"),
		NULL_ASWORKS(3002, "作品集不存在"),
		PRIVATE_ASWORKS(3003, "作品集未公开，不能查看"),
		NULL_GALLERY_ID(3004, "画廊ID不能为空"),
		NULL_GALLERY(3005, "画廊不存在"),
		NO_AUTHORITY_GALLERY(3006, "无权限查看此画廊信息"),
		NO_GALLERY_ASWORKS(3007, "画廊不存在该作品集"),
		NO_VIEWPERMISSION_CODE(3008, "作品集权限不存在"),
		NULL_ASARTWORK_ID(3009, "作品ID不能为空"),
		NULL_ASARTWORK(3010, "作品不存在"),
		NULL_MEMBER_ID(3011, "会员ID不存在"),
		NULL_MEMBER(3012, "会员不存在"),
		NULL_MEMBER_GALLERY(3013, "会员未拥有画廊"),
		NO_AUTHORITY_MODIFY_ARTWORK(3006, "无权限修改作品信息"),
		NO_AUTHORITY_MODIFY_WORKS(3006, "无权限修改作品集信息"),
		NULL_PARAM(3014, "作品参数不能为空"),
		NULL_MATERIAL(3015,"材质不存在"),
		NULL_ARTIST(3016,"艺术家不存在"),
		ERROR_IMAGE(3017,"图片有误"),
		NULL_ASWORKS_NAME(3018,"作品集名称不能为空"),
		NO_MEMBER_REGISTER(3019,"会员未注册"),
		MEMBER_EXIST(3020,"会员已注册"),
		NULL_PERMISSION_CODE(3021,"权限编码不能为空"),
		ERROR_PERMISSION(3022,"已修改至该权限"),
		NULL_WX_UNIONID(3023,"微信统一识别码为空"),
		DISABLED_MEMBER(3024,"会员已被禁用"),
		MEMBER_PROHIBITE_AUTHORIZE(3025,"会员已取消授权"),
		INVALID_WX_PARAM(3026,"无效的微信参数,请稍后再试"),
		ERROR_WX_OPERATION(3027,"微信操作异常"),
		HAS_MEMBER_WXBIND(3028,"微信已绑定其他用户"),
		REPEAT_MEMBER_WXBIND(3029,"微信已绑定该用户"),
		ERROR_MEMBER_WXBIND(3030,"微信绑定用户有误"),
		NULL_MEMBER_FACE(3031,"用户头像不能为空"),
		NULL_VIEWCODE(3032,"作品集编码不能为空"),
		ERROR_VIEWCODE(3033,"作品集编码有误"),
		NULL_PRIVIEWCODE(3034,"作品集预览编码不能为空"),
		ERROR_PRIVIEWCODE(3035,"作品集预览编码有误"),
		INVALID_WORKS_LINK(3036,"作品集预览已失效"),
		INVALID_USER(3037,"用户已失效"),
		NULL_USER(3038,"用户不存在"),
		ERROR_NUM_ASARTWORK(3039,"作品数量有误"),
		ERROR_ASWORKS(3040,"作品状态有误"),
		NULL_GALLARY_NAME(3041,"画廊名称不能为空"),
		ERROR_GALLARY_NAME(3042,"画廊名称有误");

		private int code;
		private String errMessage;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMessage() {
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}

		private BusiAppCode(int code, String errMessage) {
			this.code = code;
			this.errMessage = errMessage;
		}
	}

	public static enum IntfAppCode implements AppExceptionCode {
		NULL_VERIFY_CODE(4001, "验证码为空"),
		ERROR_VERIFY_CODE(4002, "验证码不正确"),
		NULL_PHONE(4003,"手机号不能为空"),
		ERROR_PHONE(4004,"无效的手机号"),
		NULL_PASSWORD(4005,"密码不能为空"),
		ERROR_LICENSE(4006,"请同意注册协议"),
		NULL_REPASSWD(4007,"重复密码不能为空"),
		INCONFORMITY_PASSWORD(4008,"两次密码不一致"),
		SAME_NEW_OLD_PWD(4009,"输入的密码与原来的密码一致"),
		ERROR_FORMAT_PASSWORD(4010,"请输入6-20位数字和字母组合"),
		SAME_NEWOLD_PHONE(4011,"手机号与原来的手机号不能一致"),
		ERROR_OLD_PWD(4012,"当前密码有误"),
		ERROR_PASSWORD(4013,"输入密码有误"),
		ERROR_VERIFY_TYPE(4014,"验证类型有误"),
		NULL_NICKNAME(4015,"昵称不能为空"),
		ERROR_FORMAT_NICKNAME(4016,"昵称格式有误");
		
		private int code;
		private String errMessage;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMessage() {
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}

		private IntfAppCode(int code, String errMessage) {
			this.code = code;
			this.errMessage = errMessage;
		}
	}

	public static enum CommonAppCode implements AppExceptionCode {
		JSON_STRONG_PARSE_ERROR(9001, "JSON串解析错误"), 
		JSON_OBJECT_PARSE_ERROR(9002, "JSON对象解析错误"), 
		FAIL_DATE_ERROR(9003,"日期错误,date为空"), 
		JSON_LIST_SUCCESS(0, "成功"), 
		PAGE_QUERY_NULL_OBJECT(9004, "分页查询返回值为空"), 
		QR_GENERATE_ERROR(9005, "生成二维码失败"), 
		WX_ACCESS_ERROR(9006, "微信服务异常"), 
		INVALID_TIME_TYPE(9007, "无效编码类型");
		private int code;
		private String errMessage;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMessage() {
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}

		private CommonAppCode(int code, String errMessage) {
			this.code = code;
			this.errMessage = errMessage;
		}
	}
	
	public static enum FileImageAppCode implements AppExceptionCode {
		NOT_FILE_IMAGE_TYPE(5001,"不允许上传的文件格式，请上传gif,jpg,bmp格式文件。"),
		NULL_FILE_IMAGE(5002,"文件不能为空");
		private int code;
		private String errMessage;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMessage() {
			return errMessage;
		}

		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}

		private FileImageAppCode(int code, String errMessage) {
			this.code = code;
			this.errMessage = errMessage;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1903905308902876608L;
	private String msg;
	private AppExceptionCode appExceptionCode;

	public AppExceptionCode getAppExceptionCode() {
		return appExceptionCode;
	}

	public void setAppExceptionCode(AppExceptionCode appExceptionCode) {
		this.appExceptionCode = appExceptionCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AppException(AppExceptionCode appExceptionCode) {
		super(appExceptionCode.getErrMessage());
		this.msg = appExceptionCode.getErrMessage();
		this.appExceptionCode = appExceptionCode;
	}

	public AppException(AppExceptionCode appExceptionCode, String msg) {
		super(appExceptionCode.getErrMessage() + ":" + msg);
		this.appExceptionCode = appExceptionCode;
		this.msg = msg;
	}

	public AppException(AppExceptionCode appExceptionCode, Throwable cause) {
		super(appExceptionCode.getErrMessage(), cause);
		this.appExceptionCode = appExceptionCode;
		this.msg = cause.getMessage();
	}

	public AppException(AppExceptionCode appExceptionCode, String msg, Throwable cause) {
		super(appExceptionCode.getErrMessage() + ":" + msg, cause);
		this.appExceptionCode = appExceptionCode;
		this.msg = msg + cause.getMessage();
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
					System.err.println(SpringContextUtil.getI18String("exception.printError.Fail.Info ="));
				}
			}
		}

	}

}
