package com.rongdu.cashloan.manage.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date beforeDate(Date date, int days) {
        return addOrSubDate(date,-days);
    }

    public static Date addOrSubDate(Date date, int days) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.add(java.util.Calendar.DAY_OF_YEAR, days);
        return c.getTime();

    }

    public static Date getOldDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static String getPrevious(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return format.format(calendar.getTime());
    }

    public static String getNowMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        return format.format(calendar.getTime());
    }

    public static String getNowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        return format.format(calendar.getTime());
    }

    public static String getNowDateHm(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        return format.format(calendar.getTime());
    }

    public static String getNowDateTimeHms(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        return format.format(calendar.getTime());
    }
}
