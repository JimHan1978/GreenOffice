/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */     
package com.hyetec.moa.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	public static String clearSecond(String dateTime)
	{
		if(dateTime==null){
			return null;
		}
		if(dateTime.length()>10){
			return dateTime.substring(0, 10);
		}else{
			return dateTime;
		}

	}
	/**
	 * 获取当前的时间戳
	 */
	public static long getTimestamp(){
		long millis = System.currentTimeMillis();
		return millis;
	}
	/**
	 * @Title:
	 * @Description: 获取今天日期字符串
	 *
	 * @return
	 */
	public static String getToadyString() {
		return formatDateTimeString(Calendar.getInstance(), "yyyy-MM-dd");
	}
	public static long getDateTimeString2Long(String paramString1, String paramString2) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2, Locale.SIMPLIFIED_CHINESE);
		try {
			Date localDate = localSimpleDateFormat.parse(paramString1);
			return localDate.getTime();
		} catch (ParseException localParseException) {
		}
		return 0L;
	}


	/**
	 * String to date
	 */
	public static Date getDateByDateTimeString(String dateStr, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(dateStr);
	}

	public static String clearTime(String dateTime) {
		if (dateTime == null) {
			return null;
		}
		if (dateTime.length() > 10) {
			return dateTime.substring(0, 10);
		} else {
			return dateTime;
		}

	}
	/**
	 * 格式化日期格式
	 *
	 * @param calendar
	 * @param template
	 * @return
	 */
	public static String formatDateTimeString(Calendar calendar, String template) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(template, Locale.SIMPLIFIED_CHINESE);
		return localSimpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前的时间戳
	 */
	public static String getTime(){
		DateFormat df = new SimpleDateFormat("yyyy:MM:dd");
		String time =df.format(new Date());
		return time;
	}

	private static long lastClickTime=0;

	public static boolean isFastDoubleClick(int i)
	{
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if(timeD>=0 && timeD<=i){
			return true;
		}else {
			lastClickTime = time;
			return false;
		}
	}

	public static String clearMonth(String dateTime)
	{
		if(dateTime==null){
			return null;
		}
		if(dateTime.length()>7){
			return dateTime.substring(0, 7);
		}else{
			return dateTime;
		}

	}

	public static String getTime(String dateTime){
		if(dateTime==null){
			return null;
		}
		if(dateTime.length()>16){
			return dateTime.substring(10, 16);
		}else{
			return dateTime;
		}
	}


	public static String getSQLDateTimeString(Date paramDate, String paramString)
	{
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
		if (paramDate == null) {
			paramDate = new Date();
		}
		return localSimpleDateFormat.format(paramDate);
	}




}
  