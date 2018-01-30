package com.rongdu.cashloan.core.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lsk on 2017/2/14.
 */
public class DateUtil extends tool.util.DateUtil{

    @SuppressWarnings("deprecation")
	public static Date dateAddMins(Date date, int minCnt) {
        Date d = new Date(date.getTime());
        d.setMinutes(d.getMinutes() + minCnt);
        return d;
    }
    
    /**
     * 计算时间差,单位分
     * @param date1
     * @param date2
     * @return
     */
    public static int minuteBetween(Date date1, Date date2){
		DateFormat sdf=new SimpleDateFormat(DATEFORMAT_STR_001);
		Calendar cal = Calendar.getInstance();
		try {
			Date d1 = sdf.parse(DateUtil.dateStr4(date1));
			Date d2 = sdf.parse(DateUtil.dateStr4(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 60000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
    
	/**
	 * 获取指定时间天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStartTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * String转化Date格式
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date parse(String date,String type){
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(date, pos);
		return strtodate;
		
	}

	/**
	 * 判断两个日期是否是同一天
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean sameDate(Date d1, Date d2){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		//fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置
		return fmt.format(d1).equals(fmt.format(d2));
	}
}
