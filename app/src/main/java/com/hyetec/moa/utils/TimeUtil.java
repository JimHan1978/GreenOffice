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
import java.text.SimpleDateFormat;
import java.util.Date;

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
  