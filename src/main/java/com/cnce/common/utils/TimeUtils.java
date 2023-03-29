/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cnce.common.utils;

import com.cnce.common.jenkins.JenkinsFilter;
import com.cnce.common.jenkins.JenkinsUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 时间计算工具类

 */
public class TimeUtils {

    public static Date addTime(Date date, int mount, String type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (type) {
            case "year":
                cal.add(Calendar.YEAR, mount);
                break;
            case "month":
                cal.add(Calendar.MONTH, mount);
                break;
            case "day":
                cal.add(Calendar.DATE, mount);
                break;
            case "hour":
                cal.add(Calendar.HOUR, mount);
                break;
            case "minute":
                cal.add(Calendar.MINUTE, mount);
                break;
            case "second":
                cal.add(Calendar.SECOND, mount);
                break;
            default:
                break;
        }
        return cal.getTime();
    }

    public static void timeSleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println(addTime(date, -2, "day").toString());
//    }
}