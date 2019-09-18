package com.yetoop.cloud.atlas.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.yetoop.cloud.atlas.exception.AppException;

public class TimeUtil {

	public static SimpleDateFormat sFormat = new SimpleDateFormat();


	public static final String DAY_FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	public static final String DAY_FORMAT_yyyy = "yyyy";

	public static final String DAY_FORMAT_yyyyMM = "yyyyMM";
	
	public static final String DAY_FORMAT_yyyyMMdd = "yyyyMMdd";

	public static final String DAY_FORMAT_yyyyMMddHH = "yyyyMMddHH";
	
	public static final String TIME_FORMAT_yyyyMMddHHmm = "yyyyMMddHHmm";

	public static final String TIME_FORMAT_Default_format= "yyyy-MM-dd HH:mm";
	public static final String TIME_FORMAT_HH_MM_format= "HH:mm";
	public static String getTimeInterval(Date dateBefore, Date dateAfter) {

		long timeBefore = dateBefore.getTime();
		long timeAfter = dateAfter.getTime();
		long dateTime = timeAfter - timeBefore;
		@SuppressWarnings("unused")
		long second = dateTime / 1000;//秒
		long minute = dateTime / 60000;//分钟数
		long hour = minute / 60;//小时数
		long day = hour / 24; //天
		long month = day / 30;//月
		long year = day / 365;//年
		if (year > 0) {
			return year + "年前";
		} else if (month > 0) {
			return month + "月前";
		} else if (day > 0) {
			return day + "天前";
		} else if (hour > 0) {
			return hour + "小时前";
		} else if (minute > 0) {
			return minute + "分钟前";
		}
		//		else if(second > 0){
		//			return second + "秒前";
		//		}
		else {
			return "刚刚";
		}
	}

	public static String getTimeInterval(Date dateBefore) {
		return getTimeInterval(dateBefore, new Date());
	}

	public static String getDateFormat(Date date, String pattern) {
		sFormat.applyPattern(pattern);
		return sFormat.format(date);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATA_FORMAT_SECOND_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy-MM-dd
	 */
	public static final String DATA_FORMAT_DAY_DEFAULT = "yyyy-MM-dd";

	/**
	 * MM-dd HH:mm:ss
	 */
	public static final String DATA_FORMAT_MONTH_SECOND_DEFAULT = "MM-dd HH:mm:ss";
	/**
	 * yyyyMMdd
	 */
	public static final String DATA_FORMAT_DAY_NUMBER = "yyyyMMdd";
	private static final SimpleDateFormat fmTime = new SimpleDateFormat(DATA_FORMAT_SECOND_DEFAULT);
	private static final SimpleDateFormat fmDate = new SimpleDateFormat(DATA_FORMAT_DAY_DEFAULT);

	/**
	 * 获取yyyy-MM-dd格式
	 * @param date
	 * @return
	 */
	public static String getDayFormatString(Date date) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "getDayFormatString");
		}
		return fmDate.format(date);
	}

	public static String getDayFormatString(Date date, String format) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "getDayFormatString:" + format);
		}
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 *  获取yyyy-MM-dd HH:mm:ss格式
	 * @param date
	 * @return
	 */
	public static String getSecondFormatString(Date date) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "getSecondFormatString");
		}
		return fmTime.format(date);
	}

	public static Date now() {
		return new Date();
	}

	/**
	 * 获取系统日期
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取系统时间（指定时间格式）
	 * 格式如：yyyy-MM-dd
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat fm = new SimpleDateFormat(format);
		return fm.format(new Date());
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat fm = new SimpleDateFormat(format);
		return fm.format(date);
	}

	/**
	 * 获取月末（精确到秒）
	 * @return
	 */
	public static Date getEndSecondOfMonth() {
		return DateUtils.addSeconds(truncate(addMonths(new Date(), 1), Calendar.MONTH), -1);
	}

	/**
	 * 获取月末（精确到天）
	 * @return
	 */
	public static Date getEndDayOfMonth() {
		return DateUtils.addDays(truncate(addMonths(new Date(), 1), Calendar.MONTH), -1);
	}

	public static Date getStartDayOfNextMonth() {
		return DateUtils.truncate(addMonths(new Date(), 1), Calendar.MONTH);
	}

	/**
	 * 比较两个日期大小（精确到天）
	 * @param source
	 * @param dest
	 * @return
	 */
	public static int commpareDate(Date source, Date dest) {
		if (source == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "commpareDate:" + source + "," + dest);
		}
		if (dest == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "commpareDate:" + source + "," + dest);
		}
		try {
			return DateUtils.truncatedCompareTo(source, dest, Calendar.DAY_OF_MONTH); //DateUtils's method
		} catch (RuntimeException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, e);
		}
	}

	/**
	 * 比较两个日期大小（精确到秒）
	 * @param sourceTime
	 * @param destTime
	 * @return
	 */
	public static int commpareTime(Date sourceTime, Date destTime) {
		if (sourceTime == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "commpareTime:" + sourceTime + ","
					+ destTime);
		}
		if (destTime == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "commpareTime:" + sourceTime + ","
					+ destTime);
		}
		try {
			return DateUtils.truncatedCompareTo(sourceTime, destTime, Calendar.SECOND); //DateUtils's method
		} catch (RuntimeException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, e);
		}
	}

	/**
	 * 小于当前日期之前（精确到天）
	 * @param date
	 * @return
	 */
	public static boolean beforeTheCurrentDate(Date date) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "beforeTheCurrentDate");
		}
		try {
			return commpareDate(date, new Date()) == -1 ? true : false;
		} catch (RuntimeException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, e);
		}
	}

	/**
	 * 大于当前日期之前（精确到天）
	 * @param date
	 * @return
	 */
	public static boolean afterTheCurrentDate(Date date) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "afterTheCurrentDate");
		}
		try {
			return commpareDate(date, new Date()) == 1 ? true : false;
		} catch (RuntimeException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, e);
		}
	}

	/**
	 * 小于当前日期之前（精确到秒）
	 * @param date
	 * @return
	 */
	public static boolean beforeTheCurrentTime(Date time) {
		if (time == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "beforeTheCurrentTime");
		}
		try {
			return commpareTime(time, new Date()) == -1 ? true : false;
		} catch (RuntimeException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, e);
		}
	}

	/**
	 * 大于当前日期之前（精确到秒）
	 * @param date
	 * @return
	 */
	public static boolean afterTheCurrentTime(Date time) {
		if (time == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "afterTheCurrentTime");
		}
		try {
			return commpareTime(time, new Date()) == 1 ? true : false;
		} catch (RuntimeException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, e);
		}
	}

	public static Date addMonths(Date date, int i) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "addMonths:" + i);
		}
		return DateUtils.addMonths(date, i);
	}

	public static Date addDays(Date startDt, int intValue) {
		if (startDt == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "addDays:" + intValue);
		}
		return DateUtils.addDays(startDt, intValue);
	}

	public static Date addHours(Date startDt, int intValue) {
		if (startDt == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "addHours:" + intValue);
		}
		return DateUtils.addHours(startDt, intValue);
	}
	
	public static Date addMinutes(Date startDt, int intValue) {
		if (startDt == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "addMinutes:" + intValue);
		}
		return DateUtils.addMinutes(startDt, intValue);
	}
	
	public static Date addSeconds(Date startDt, int intValue) {
		if (startDt == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "addSeconds:" + intValue);
		}
		return DateUtils.addSeconds(startDt, intValue);
	}

	public static Date addYears(Date startDt, int intValue) {
		if (startDt == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "addYears:" + intValue);
		}
		return DateUtils.addYears(startDt, intValue);

	}

	public static Date truncate(Date date, int i) {
		if (date == null) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, "truncate:" + i);
		}
		return DateUtils.truncate(date, i);
	}

	public static Date parseDate(String string, String dataFormatDayDefault) {
		try {
			return DateUtils.parseDate(string, dataFormatDayDefault);
		} catch (ParseException e) {
			throw new AppException(AppException.CommonAppCode.FAIL_DATE_ERROR, string + ",dataFormat:"
					+ dataFormatDayDefault, e);
		}
	}

	/**
	 * 取当前月份
	 * @return
	 */
	public static String getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR);
		Integer month = cal.get(Calendar.MONTH) + 1;
		String convertYear = String.valueOf(year);
		String convertMonth = String.valueOf(month);
		if(convertMonth.length()<2){
			convertMonth="0"+convertMonth;
		}
		String time = convertYear + convertMonth;
		return time;
	}

	/**
	 * 取当前分页首个月份为参数，进行查询，装入list列表
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static List<String> getMonthList(String currentMonth, Integer times) throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat stdFormatter = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String startDateStr = currentMonth;
		Date startDate = stdFormatter.parse(startDateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		for (int i = 0; i < times; i++) {
			//System.out.println(formatter.format(startDate));
			list.add(formatter.format(startDate));
			c.add(Calendar.MONTH, -1);
			startDate = c.getTime();

		}
		return list;
	}

	/**
	 * 取当前给出的一个月份值，取上一个月的值
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String getLastMonth(String monthStr) throws ParseException {
		Integer year = Integer.parseInt(monthStr.substring(0, 4));
		Integer month = Integer.parseInt(monthStr.substring(4, 6));
		String lastMonthStr = null;
		if (month != 1) {
			Integer lastMonth = month - 1;
			lastMonthStr = String.valueOf(year) + '0' + String.valueOf(lastMonth);
		} else {
			Integer lastMonth = 12;
			Integer lastYear = year - 1;
			lastMonthStr = String.valueOf(lastYear) + String.valueOf(lastMonth);
		}
		return lastMonthStr;
	}

	/**
	 * 
	 * 计算两个日期相差的月份数
	 * 
	 * @param startMonth  开始月份
	 * @param endMonth 结束月份
	 * @return  相差的月份数
	 * @throws ParseException
	 */
	public static int countMonths(String startMonth, String endMonth) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date begin = sdf.parse(startMonth);
		Date end = sdf.parse(endMonth);
		int months = (end.getYear() - begin.getYear()) * 12 + (end.getMonth() - begin.getMonth());
		return months;
	}

	public static void main(String[] args) throws ParseException {

		Date date1 = new Date();
		System.out.println(date1);

		System.out.println(truncate(date1, Calendar.HOUR));
	}

}
