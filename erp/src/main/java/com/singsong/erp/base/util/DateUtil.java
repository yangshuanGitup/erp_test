package com.singsong.erp.base.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

	public static final String DATETIME_PATTERN_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATETIME_PATTERN_YEAR_DEFAULT = "yyyy";

	public static final String DATETIME_PATTERN_MONTH_DEFAULT = "yyyy-MM";

	public static final String DATETIME_PATTERN_DAY_DEFAULT = "yyyy-MM-dd";
	
	public static final String DATETIME_PATTERN_TIME_SPECIAL = "yyyyMMddHHmmss";
	
	public static final String DATETIME_PATTERN_TIME_SPECIAL_MS = "yyyyMMddHHmmsss";//毫秒

	public static final String DATETIME_PATTERN_MONTH_SPECIAL = "yyyyMM";

	public static final String DATETIME_TIME_PATTERN_DAY_SPECIAL = "yyyyMMdd";

	public static final DateTimeFormatter PATTERN_TIME_DEFAULT = DateTimeFormat.forPattern(DATETIME_PATTERN_TIME_DEFAULT);
	
	public static final DateTimeFormatter PATTERN_DAY_DEFAULT  = DateTimeFormat.forPattern(DATETIME_PATTERN_DAY_DEFAULT);

	public static final DateTimeFormatter PATTERN_TIME_SPECIAL = DateTimeFormat.forPattern(DATETIME_PATTERN_TIME_SPECIAL);
	
	public static final DateTimeFormatter PATTERN_TIME_SPECIAL_MS = DateTimeFormat.forPattern(DATETIME_PATTERN_TIME_SPECIAL_MS);
	
	public static Date parse(String str, DateTimeFormatter formatter) {
		return DateTime.parse(str, formatter).toDate();
	}
	public static long getMillis(DateTime date) {
		return date.getMillis();
	}
	public static Date getCurrentDate(){
		return new DateTime().toDate();
	}
	public static String getCurrentDateStr(){
		return new DateTime().toString(PATTERN_TIME_DEFAULT);
	}
	public static String getCurrentDateStr(DateTimeFormatter formatter){
		return new DateTime().toString(formatter);
	}
	public static String getCurrentDay(){
		return new DateTime().toString(PATTERN_DAY_DEFAULT);
	}
	public static String getCurrentTimestamp(){
		return new DateTime().toString(PATTERN_TIME_SPECIAL_MS);
	}
	public static long currentTimeMillis(){
		return System.currentTimeMillis();
	}
}
