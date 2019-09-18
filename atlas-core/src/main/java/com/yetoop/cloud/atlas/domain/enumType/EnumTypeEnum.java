package com.yetoop.cloud.atlas.domain.enumType;

import com.yetoop.cloud.atlas.exception.AppException;

public class EnumTypeEnum {

	public static enum TimeTypeEnum {
		HOUR("HOUR") {
			@Override
			public TimeTypeEnum getNextType() {
				return DAY;
			}
		},
		DAY("DAY") {
			@Override
			public TimeTypeEnum getNextType() {
				return MONTH;
			}
		},
		WEEK("WEEK") {
			@Override
			public TimeTypeEnum getNextType() {
				return null;
			}
		},
		MONTH("MONTH") {
			@Override
			public TimeTypeEnum getNextType() {
				return YEAR;
			}
		},
		YEAR("YEAR") {
			@Override
			public TimeTypeEnum getNextType() {
				return null;
			}
		},
		ALL("ALL") {
			@Override
			public TimeTypeEnum getNextType() {
				return null;
			}
		};
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		private TimeTypeEnum(String type) {
			this.type = type;
		}

		public TimeTypeEnum getNextType() {
			return null;
		}

		public static TimeTypeEnum getByType(String type) {
			TimeTypeEnum result = null;
			for (TimeTypeEnum timeTypeEnum : TimeTypeEnum.values()) {
				if (timeTypeEnum.getType().equals(type)) {
					result = timeTypeEnum;
					break;
				}
			}
			if (result == null) {
				throw new AppException(AppException.CommonAppCode.INVALID_TIME_TYPE, type);
			}
			return result;
		}
	}

	public static enum WxConfigTypeEnum {
		PCWXLOGIN("pcWxLogin"), PCWXBIND("pcWxBind");
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		private WxConfigTypeEnum(String type) {
			this.type = type;
		}

	}

	public static enum SmsMessageEnum {
		SMS_SERV_URL("SMS_SERV_URL"), SMS_SERV_PORT("SMS_SERV_PORT"), SMS_ACCOUNT_SID(
				"SMS_ACCOUNT_SID"), SMS_AUTH_TOKEN("SMS_AUTH_TOKEN"), SMS_APP_ID("SMS_APP_ID");

		private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		private SmsMessageEnum(String code) {
			this.code = code;
		}
	}

	public enum WorksFavoriteFlagEnum {
		Y("Y"), N("N");
		private String flag;

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		private WorksFavoriteFlagEnum(String flag) {
			this.flag = flag;
		}
	}

	public enum StateEnum {
		NORMAL("1"), // 正常
		SCRAP("0"), // 作废
		DESTROY("9");// 删除
		private String state;

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		private StateEnum(String state) {
			this.state = state;
		}
	}

}
