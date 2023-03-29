package com.cnce.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 两个时间之差
     * @param startDate
     * @param endDate
     * @return 分钟
     */
    public static Integer getBetweenMinutes(Date startDate, Date endDate) {
        Integer minutes = 0;
        try {
            if(startDate!=null&&endDate!=null) {
                long ss = 0;
                if(startDate.before(endDate)) {
                    ss = endDate.getTime() - startDate.getTime();
                }else {
                    ss = startDate.getTime() - endDate.getTime();
                }
                minutes = Integer.valueOf((int) (ss/(60*1000))) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes;
    }

    /**
     * 两个时间之差
     * @param smdate
     * @param bdate
     * @return 天数
     */
    public static Integer getBetweenDays(String smdate, String bdate) {
        long between_days = 0L;

        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Integer beteenDays(String starDate, String endDate){
        long between_days = 0L;
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date star = dft.parse(starDate);
            Date endDay=dft.parse(endDate);
            Long starTime=star.getTime();
            Long endTime=endDay.getTime();
            Long num=endTime-starTime;
            Long betweenDays = num/24/60/60/1000;
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 判断日期是不是周末
     * @param bDate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
    }

    /**
     * 获取当天是一星期中的第几天
     * @return
     */
    public static String getNowOfWeekDay(){
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return String.valueOf(dayOfWeek);
    }

    public static String getNowTimeHHmm(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());
    }

    public static boolean nowBetweenThis(String startStr,String endStr){
        String[] startArr = startStr.split(":");
        String[] endArr = endStr.split(":");
        String[] nowArr = getNowTimeHHmm().split(":");
        if(Integer.parseInt(startArr[0])<=Integer.parseInt(nowArr[0]) &&
                Integer.parseInt(nowArr[0]) <= Integer.parseInt(endArr[0])
                && Integer.parseInt(startArr[1])<=Integer.parseInt(nowArr[1])){
            return true;
        }
        return false;
    }

    /**
     * 当前时间向前/后推n分钟
     * @param minute 加减的分钟数
     * @return Date
     */
    public static Date nowBeforeNMin(int minute){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,minute);
        return cal.getTime();
    }
    /**
     * 当前时间向前/后推n小时
     * @param hour 加减的小时
     * @return Date
     */
    public static Date nowBeforeNHour(int hour){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR,hour);
        return cal.getTime();
    }
    /**
     * 当前时间向前/后推n年
     * @param year 加减的小时
     * @return Date
     */
    public static Date nowBeforeNyear(int year){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR,year);
        return cal.getTime();
    }

    // 得到日期范围list
    public static List<String> getDates(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        List<Date> lDate = new ArrayList<>();
        lDate.add(begin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        // 检查日期是否在指定日期之后
        while (end.after(calBegin.getTime())){
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> timeStringList=new ArrayList<>();
        for (Date datePes :lDate){
            String format = sdf.format(datePes);
            timeStringList.add(format);
        }
        return timeStringList;
    }

    public static void main(String[] args) {
        System.out.println(getNowOfWeekDay());
        System.out.println(getNowTimeHHmm());
        System.out.println(nowBetweenThis("17:00","24:00"));

        System.out.println(nowBeforeNMin(-10));

        System.out.println(getBetweenDays("2023-1-6", "2023-1-13"));
    }

}
