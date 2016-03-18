package com.cbsys.iclock.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * 日期工具类
 * @author albert
 *
 */
public class DateUtil {
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String COMMONE_DATE_FORMAT = "yyyy-M-d";
	public static final String NORMAL_MONTH_DATE_FORMAT = "MM-dd";
	public static final String NORMAL_SQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String NORMAL_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String ADC_MSG_TIMESTAMP = "yyyyMMddHHmmssSSSS";
	public static final String ADC_MSG_SID = "yyyyMMddHHmmss";
	public static final String MONTH_DATE_FORMAT = "yyyy-MM";
	public static final String YEAR_DATE_FORMAT = "yyyy";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String HOUR_FORMAT = "HH:mm";
	public static final String TIME_SPLIT = ":";
	public static final Pattern TIME_HM_PATTERN = Pattern.compile("([0-1][0-9]|[2][0-3]):[0-5][0-9]");
	public static final Pattern TIME_HMS_PATTERN = Pattern.compile("([0-1][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]");

	public static Timestamp getTimeByZKTimestamp(long time) {
		long second = time % 60; //[0,59]
		time /= 60;
		long minute = time % 60; //[0,59]
		time /= 60;
		long hour = time % 24; //[0,23]
		time /= 24;
		long date = time % 31 + 1; //[1,31]
		time /= 31;
		long month = time % 12; //[0,11]
		time /= 12;
		long year = time + 100; //1900开始
		year = year + 1900;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.set((int) year, (int) month, (int) date, (int) hour, (int) minute, (int) second);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static long getZKTimestamp(Timestamp time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());
		long year = cal.get(Calendar.YEAR) - 1900;
		long month = cal.get(Calendar.MONTH);
		long day = cal.get(Calendar.DAY_OF_MONTH);
		long hour = cal.get(Calendar.HOUR_OF_DAY);
		long minute = cal.get(Calendar.MINUTE);
		long second = cal.get(Calendar.SECOND);
		long zktime = ((year - 100) * 12 * 31 + month * 31 + day - 1) * (24 * 60 * 60) + (hour * 60 + minute) * 60 + second;
		return zktime;
	}


}
