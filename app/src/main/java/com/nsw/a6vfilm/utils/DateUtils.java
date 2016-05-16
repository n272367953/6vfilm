/*
 *	Copyright (c) 2015, Red Finance Information Technologies
 *	All rights reserved.
 *  
 *  @author: liujunbo	
 */
package com.nsw.a6vfilm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	/**
	 * 时间格式（短）
	 */
	private static final SimpleDateFormat dateStringShortFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	
	/**
	 * 时间格式（全）
	 */
	private static final SimpleDateFormat dateStringFullFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
	
	/**
	 * 生成时间,只支持yyyy-MM-dd格式
	 * @return
	 */
	public static Date stringToDateShort(String sDate){
		try {
			return dateStringShortFormat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成时间,只支持yyyy-MM-dd hh:mm:ss格式
	 * @return
	 */
	public static Date stringToDateFull(String sDate){
		try {
			return dateStringFullFormat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 转换时间，yyyy-MM-dd hh:mm:ss格式转换为yyyy-MM-dd格式
	 * @return
	 */
	public static String dateStringFullToShort(String dateStringFull){
		try {
			return dateStringShortFormat.format(dateStringFullFormat.parse(dateStringFull));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 生成yyyy-MM-dd格式时间字符串
	 * @return
	 */
	public static String getFormatDateString(int year, int month, int day){
		return new StringBuilder().append(year).append("-")
                .append((month) < 10 ? "0" + (month) : (month))
                .append("-")
                .append((day < 10) ? "0" + day : day).toString();
	}
	
	/**
	 * 两个日期是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2){
        String sDate1 = dateStringShortFormat.format(date1);  
        String sDate2 = dateStringShortFormat.format(date2);  

        if(sDate1.equals(sDate2)){ 
        	return true;
        }else{
        	return false;
        }  
	}
}
