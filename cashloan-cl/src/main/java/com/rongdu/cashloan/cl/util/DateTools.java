package com.rongdu.cashloan.cl.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTools {

    public static void main(String[] args) {
        // 获取本周的每一天日期
        // 2017-12-18
        // ...
        // 2017-12-24
        List<String> lst = getALlweekDays();//获取当前日期下，一周的每一天


        // 获取当月的每一天日期
        // 2017-12-01
        // 2017-12-02
        // ...
        // 2017-12-31
        List <String> lst2 = getAllDaysMonth();

        // 获取特定日期所在那周的每一天日期
        // 2014-07-28
        // ...
        // 2014-08-03
        Date d=DateTools.paraseStringToDate("2014-8-1");
        List <String> lst3 = DateTools.getAllweekDays(d);

        // 获取特定日期所在那月的每一天日期
        // 2014-06-01
        // ...
        // 2014-06-30
        Date d2=DateTools.paraseStringToDate("2014-6-1");
        List <String> lst4 = DateTools.getAllDaysMonthByDate(d2);


        // 获取某段日期内所有的日期
        // 2012-11-15
        // ...
        // 2012-12-20
        String start = "2017-11-15";
        String end = "2017-12-20";
        List <String> lst5 = getSdateToEdate(start,end);
    }

    private static final int FIRST_DAY = Calendar.MONDAY;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String getMonthStart()//获取月初日期
    {
        Date d = new Date();
        // 月初
        // System.out.println("月初" + sdf.format(getMonthStart(d)));
        return sdf.format(getMonthStart(d));
    }
    public static String getMonthStartStr(Date d)//根据传入日期来获取一个月的开始时间
    {
        return sdf.format(getMonthStart(d));
    }
    public static String getMonthEndStr(Date d)//根据传入时间获取一个月月末时间
    {

        return sdf.format(getMonthEnd(d));
    }
    public static List<String>getAllDaysMonthByDate(Date d)//根据传入的日期获取所在月份所有日期
    {
        List<String> lst=new ArrayList();
        Date date = getMonthStart(d);
        Date monthEnd = getMonthEnd(d);
        while (!date.after(monthEnd)) {
            //System.out.println(sdf.format(date));
            lst.add(sdf.format(date));
            date = getNext(date);
        }
        return lst;
    }
    public static Date paraseStringToDate(String timestr )//将字符串转化为日期
    {
        Date date=null;


        Format f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = (Date) f.parseObject(timestr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public String getMonthEnd()//获取月末日期
    {
        Date d = new Date();
        return sdf.format(getMonthEnd(d));
    }
    public static List<String>getAllDaysMonth()
    {
        List<String> lst=new ArrayList();
        Date d = new Date();
        // 月初
        // System.out.println("月初" + sdf.format(getMonthStart(d)));
        // 月末
        //System.out.println("月末" + sdf.format(getMonthEnd(d)));

        Date date = getMonthStart(d);
        Date monthEnd = getMonthEnd(d);
        while (!date.after(monthEnd)) {
            //System.out.println(sdf.format(date));
            lst.add(sdf.format(date));
            date = getNext(date);
        }
        return lst;
    }
    private static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }

    private static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }

    private static Date getNext(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    public static String getWeekStartDay(Date d) {//根据日期来获取一周的第一天
        Calendar c = Calendar.getInstance();
        List <String>lst=new ArrayList();
        c.setTime(d);
        setToFirstDay(c);
        for (int i = 0; i < 7; i++) {
            String day = printDay(c);
            lst.add(day);
            c.add(Calendar.DATE, 1);
        }
        return lst.get(0);
    }
    public static String getWeekEndtDay(Date d) {//根据日期来获取一周的最后一天
        Calendar c = Calendar.getInstance();
        List <String>lst=new ArrayList();
        c.setTime(d);
        setToFirstDay(c);
        for (int i = 0; i < 7; i++) {
            String day = printDay(c);
            lst.add(day);
            c.add(Calendar.DATE, 1);
        }
        return lst.get(6);
    }
    public static List<String> getAllweekDays(Date d) {//根据日期来获取其所在周的每一天
        Calendar c = Calendar.getInstance();
        List <String>lst=new ArrayList();
        c.setTime(d);
        setToFirstDay(c);
        for (int i = 0; i < 7; i++) {
            String day = printDay(c);
            lst.add(day);
            c.add(Calendar.DATE, 1);
        }
        return lst;
    }
    public static List<String> getALlweekDays() {
        List<String>lst=new ArrayList();
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        for (int i = 0; i < 7; i++) {
            String day=printDay(calendar);
            lst.add(day);
            calendar.add(Calendar.DATE, 1);
        }
        return lst;
    }

    private static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    private static String  printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    private static List<Date> findDates(Date dBegin, Date dEnd)
    {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    public static List<String> getSdateToEdate(String startDate,String endDate){
        Calendar cal = Calendar.getInstance();
        String start = startDate;
        String end = endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try{
            dBegin = sdf.parse(start);
            dEnd = sdf.parse(end);
        }catch (Exception e){
        }
        List<Date> lDate = findDates(dBegin, dEnd);
        List<String> lst = new ArrayList();
        for (Date date : lDate)
        {
            lst.add(sdf.format(date));
        }
        return lst;
    }
}
