package com.king.frame.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * 时间工具类
 *
 * @author king
 * @date @date 2019-11-27 13:19
 */

object TimeUtil {

    val SECOND_STR = "yyyy-MM-dd HH:mm:ss"
    val MINUTE_STR = "yyyy-MM-dd HH:mm"
    val DAY_STR = "yyyy-MM-dd"

    /**
     * 时间转成指定格式的字符串
     *
     * @param date
     * @return
     */
    fun dateToStr(date: Date, formatStr: String): String {
        val sdf = SimpleDateFormat(formatStr)
        return sdf.format(date)
    }

    /**
     * 时间转成yyyy-MM-dd格式的字符串
     *
     * @param date
     * @return
     */
    fun dateToDayStr(date: Date): String {
        val sdf = SimpleDateFormat(DAY_STR)
        return sdf.format(date)
    }

    /**
     * 时间转成yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date
     * @return
     */
    fun dateToSecondStr(date: Date): String {
        val sdf = SimpleDateFormat(SECOND_STR)
        return sdf.format(date)
    }

    /**
     * 字符串转成Date
     *
     * @param dateStr
     * @return
     */
    fun dayToDate(dateStr: String, formatStr: String): Date? {
        var d: Date? = null
        val sdf = SimpleDateFormat(formatStr)
        try {
            d = sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return d
    }

    /**
     * 字符串转成Date
     *
     * @param dateStr
     * @return
     */
    fun dayStrToDate(dateStr: String): Date? {
        var d: Date? = null
        val sdf = SimpleDateFormat(DAY_STR)
        try {
            d = sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return d
    }

    /**
     * 字符串转成Date
     *
     * @param dateStr
     * @return
     */
    fun secondStrToDate(dateStr: String): Date? {
        var d: Date? = null
        val sdf = SimpleDateFormat(SECOND_STR)
        try {
            d = sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return d
    }

    /**
     * 获取当前时间转成指定格式字符串
     *
     * @return
     */
    fun getCurrentTime(formatStr: String): String {
        val calendar = Calendar.getInstance()
        val d = calendar.time
        val sdf = SimpleDateFormat(formatStr)
        return sdf.format(d)
    }

    /**
     * 获取相隔天数
     *
     * @param dateStart
     * @param dateEnd
     * @return
     */
    @Throws(ParseException::class)
    fun getDiscrepantDays(dateStart: String, dateEnd: String): Long {
        val sdf = SimpleDateFormat(SECOND_STR)
        val begin = sdf.parse(dateStart)
        val end = sdf.parse(dateEnd)
        return (end.time - begin.time) / 1000 / 60 / 60 / 24
    }

    /**
     * 获取相隔天数
     *
     * @param dateStart
     * @param dateEnd
     * @return
     */
    fun getDiscrepantDays(dateStart: Date, dateEnd: Date): Long {
        return (dateEnd.time - dateStart.time) / 1000 / 60 / 60 / 24
    }

    /**
     * 获取相隔分钟
     *
     * @param dateStart
     * @param dateEnd
     * @return interval  负数代表前面的时间比后面大
     */
    fun getIntervalMinutes(dateStart: Date, dateEnd: Date): Long {

        return (dateEnd.time - dateStart.time) / 1000 / 60
    }

    /**
     * 获取相隔分钟
     *
     * @param dateStart
     * @param dateEnd
     * @return interval  负数代表前面的时间比后面大
     */
    @Throws(ParseException::class)
    fun getIntervalMinutes(dateStart: String, dateEnd: String): Long {

        val sdf = SimpleDateFormat(SECOND_STR)
        val begin = sdf.parse(dateStart)
        val end = sdf.parse(dateEnd)
        return (end.time - begin.time) / 1000 / 60
    }

    /**
     * 毫秒数转成yyyy-MM-dd HH:mm:ss字符串
     * @param timeMillis
     * @return
     */
    fun timeMillisToStr(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis

        val sdf = SimpleDateFormat(SECOND_STR)
        return sdf.format(calendar.time)
    }
}
