package com.king.frame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author king
 * @date 2017/3/2 10:11
 */

public class TimeUtil {

    public static final String SECOND_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_STR = "yyyy-MM-dd HH:mm";
    public static final String DAY_STR = "yyyy-MM-dd";

    /**
     * 时间转成指定格式的字符串
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 时间转成yyyy-MM-dd格式的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDayStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_STR);
        return sdf.format(date);
    }

    /**
     * 时间转成yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date
     * @return
     */
    public static String dateToSecondStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SECOND_STR);
        return sdf.format(date);
    }

    /**
     * 字符串转成Date
     *
     * @param dateStr
     * @return
     */
    public static Date dayToDate(String dateStr, String formatStr) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 字符串转成Date
     *
     * @param dateStr
     * @return
     */
    public static Date dayStrToDate(String dateStr) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_STR);
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 字符串转成Date
     *
     * @param dateStr
     * @return
     */
    public static Date secondStrToDate(String dateStr) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(SECOND_STR);
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 获取当前时间转成指定格式字符串
     *
     * @return
     */
    public static String getCurrentTime(String formatStr) {
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(d);
    }

    /**
     * 获取相隔天数
     *
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static long getDiscrepantDays(String dateStart, String dateEnd) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SECOND_STR);
        Date begin = sdf.parse(dateStart);
        Date end = sdf.parse(dateEnd);
        return (end.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24;
    }

    /**
     * 获取相隔天数
     *
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static long getDiscrepantDays(Date dateStart, Date dateEnd) {
        return (dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24;
    }

    /**
     * 获取相隔分钟
     *
     * @param dateStart
     * @param dateEnd
     * @return interval  负数代表前面的时间比后面大
     */
    public static long getIntervalMinutes(Date dateStart, Date dateEnd) {
        long interval = (dateEnd.getTime() - dateStart.getTime()) / 1000 / 60;

        return interval;
    }

    /**
     * 获取相隔分钟
     *
     * @param dateStart
     * @param dateEnd
     * @return interval  负数代表前面的时间比后面大
     */
    public static long getIntervalMinutes(String dateStart, String dateEnd) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(SECOND_STR);
        Date begin = sdf.parse(dateStart);
        Date end = sdf.parse(dateEnd);
        long interval = (end.getTime() - begin.getTime()) / 1000 / 60;
        return interval;
    }

    /**
     * 毫秒数转成yyyy-MM-dd HH:mm:ss字符串
     * @param timeMillis
     * @return
     */
    public static String timeMillisToStr(long timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        SimpleDateFormat sdf = new SimpleDateFormat(SECOND_STR);
        return sdf.format(calendar.getTime());
    }
}
