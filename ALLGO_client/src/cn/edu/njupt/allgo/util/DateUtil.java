package cn.edu.njupt.allgo.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * "yyyy年MM月dd日HH:mm" , "EEE MMM dd HH:mm:ss Z yyyy"
	 * @param time1
	 * @return
	 */
	public static String changeDate(String time1){
		return showDate(time1 , "yyyy年MM月dd日HH:mm" , "EEE MMM dd HH:mm:ss Z yyyy");
	}
	
	/**
	 * 用于转换格式显示时间
	 * @param time1		需要转换的时间数据
	 * @return
	 */
	public static String showDate(String time1) {
		return showDate(time1 , "EEE MMM dd HH:mm:ss Z yyyy" , "yyyy年MM月dd日 HH:mm");
	}
	
	/**
	 * 
	 * @param time1		需要转换的时间数据
	 * @param result	输出格式
	 * @return
	 */
	public static String showDate(String time1 ,String result) {
		return showDate(time1 , "EEE MMM dd HH:mm:ss Z yyyy" , result);
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
			SimpleDateFormat sf = new SimpleDateFormat(source , Locale.ENGLISH);
			sf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			Date date = null;
			try {
				date = sf.parse(time1);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			DateFormat sdf = new SimpleDateFormat(result , Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00")); // 设置时区为GMT+08:00 
			    str = sdf.format(date);
		}
		return str;
	}

	/**
	 * 智能输出时间差
	 * @param start
	 * @param end
	 * @return
	 */
	public static String smartDate(String start,String end){
		String result = "";
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd Z yyyy", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		try {
			Date now = new Date();
			Date startdate = df.parse(start);
			long l_end = 0 ;
			long l_start=startdate.getTime() - now.getTime();
			
			if(end == null || end.equals("")){
				l_end=l_start+3*60*60*1000;
			}else{
				Date enddate = df.parse(end);
				l_end= enddate.getTime() - now.getTime();
			}

			if(l_end <= 0){				//是否已经结束
				result = showDate(start);
			}else if(l_start <= 0){		//是否已经开始
				result = showDate(start);
			}else {
				String startTime = sdf.format(startdate);
				Date starttime = sdf.parse(startTime);
				String nowTime = sdf.format(now);
				Date nowtime = sdf.parse(nowTime);
				long long_start=starttime.getTime() - nowtime.getTime();
				int day=(int) (long_start/(24*60*60*1000));
				if(day > 2){
					SimpleDateFormat year = new SimpleDateFormat("yyyy",Locale.ENGLISH);
					year.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
					String now_year = year.format(now);
					String start_year = year.format(startdate);
					showDate(start , "EEE MMM dd HH:mm:ss Z yyyy" , "yyyy");
					if(now_year.equals(start_year)){
						result = showDate(start , "EEE MMM dd HH:mm:ss Z yyyy" , "MM月dd日 HH:mm");
					}else{
						result = showDate(start);
					}
				}else{
					if(day == 2){
						result = "后天"+showDate(start , "EEE MMM dd HH:mm:ss Z yyyy" , "HH:mm");
					}else if(day == 1){
						result = "明天"+showDate(start , "EEE MMM dd HH:mm:ss Z yyyy" , "HH:mm");
					}else{
						result = "今天"+showDate(start , "EEE MMM dd HH:mm:ss Z yyyy" , "HH:mm");
					}
				}
			}
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		   
		  
		return result;
	}
	
	/**
	 * 判断时间是否结束
	 * @return	1.已结束2.进行中3.未开始
	 */
	public static int judgeDate(String start , String end){
		int flag  = 1;
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		try {
			Date now = new Date();
			Date startdate = df.parse(start);
			long l_end = 0 ;
			long l_start=startdate.getTime() - now.getTime();
			
			if(end == null || end.equals("")){
				l_end=l_start+3*60*60*1000;
			}else{
				Date enddate = df.parse(end);
				l_end= enddate.getTime() - now.getTime();
			}

			if(l_end <= 0){
				flag = 1;
			}else if(l_start <= 0){
				flag = 2;
			}else {
				flag = 3;
			}
			
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	
		return flag;
	}
	
/*	public static String saveDate(String time1){
		
		return saveDate(time1 , "E MMM dd HH:mm:ss Z yyyy" , "EEE MMM dd HH:mm:ss Z yyyy");
	}*/
	
	public static String saveDate(String time1 , String source ,String result) {
		String str = "";
		if(!time1.equals("")){
			SimpleDateFormat sf = new SimpleDateFormat(source, Locale.ENGLISH);
			sf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
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
	
	/**
	 * 比较时间
	 * 0.相等 <0.大于 >0.小于
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int compareDate(String time1 , String time2){
		Date date1 = formatDate(time1);
		Date date2 = formatDate(time2);
		return date1.compareTo(date2);
	}
	
	
	/**
	 * String转换到Date
	 * @param time	"EEE MMM dd HH:mm:ss Z yyyy"
	 * @return
	 */
	public static Date formatDate(String time){
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		sf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		Date date = null;
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}

