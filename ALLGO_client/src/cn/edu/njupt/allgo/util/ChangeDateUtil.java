package cn.edu.njupt.allgo.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.util.TimeZone;

public class ChangeDateUtil {


	public static String changeDate(String time1){
		return showDate(time1 , "yyyy年MM月dd日HH:mm" , "EEE MMM dd hh:mm:ss z yyyy");
	}
	
	/**
	 * 用于转换格式显示时间
	 * @param time1		需要转换的时间数据
	 * @return
	 */
	public static String showDate(String time1) {
		return showDate(time1 , "EEE MMM dd hh:mm:ss z yyyy" , "yyyy年MM月dd日 HH:mm");
	}
	
	/**
	 * 
	 * @param time1		需要转换的时间数据
	 * @param result	输出格式
	 * @return
	 */
	public static String showDate(String time1 ,String result) {
		return showDate(time1 , "EEE MMM dd hh:mm:ss z yyyy" , result);
	}
	
	
	/**
	 * 
	 * @param time1 	需要转换的时间数据
	 * @param source 	输入格式
	 * @param result 	输出格式
	 * @return
	 */
	public static String showDate(String time1 , String source ,String result) {
		String str = "";
		if(!time1.equals("")){
			SimpleDateFormat sf = new SimpleDateFormat(source, Locale.ENGLISH);
			Date date = null;
			try {
				date = sf.parse(time1);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			DateFormat sdf = new SimpleDateFormat(result, Locale.CHINA);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00")); // 设置时区为GMT+08:00 
			    str = sdf.format(date);
		}
		return str;
	}
	
	public static String saveDate(String time1){
		
		return saveDate(time1 , "E MMM dd hh:mm:ss z yyyy" , "EEE MMM dd hh:mm:ss z yyyy");
	}
	
	public static String saveDate(String time1 , String source ,String result) {
		String str = "";
		if(!time1.equals("")){
			SimpleDateFormat sf = new SimpleDateFormat(source, Locale.CHINA);
			Date date = null;
			try {
				date = sf.parse(time1);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			DateFormat sdf = new SimpleDateFormat(result, Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00")); // 设置时区为GMT+08:00 
			    str = sdf.format(date);
		}
		return str;
	}
	
}

