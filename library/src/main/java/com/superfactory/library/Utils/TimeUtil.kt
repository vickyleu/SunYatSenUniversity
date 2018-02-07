package com.superfactory.library.Utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  15:27:30
 * @ClassName 这里输入你的类名(或用途)
 */
object TimeUtil {

    fun takeNowTime(format: String): String? {
        try {
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            return sdf.format(Date())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun takeNowDate(format: String, timeFormatter: String): Date? {
        try {
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            return sdf.parse(timeFormatter)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun takeNowYear(): Int {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            return calendar.get(Calendar.YEAR)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 2018
    }

    fun takeNowMonth(): Int {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            return calendar.get(Calendar.MONTH)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 1
    }

    fun takeNowDay(): Int {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            return calendar.get(Calendar.DAY_OF_MONTH)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 1
    }


    fun takeNowTime(format: String, sourceFormat: String, time: String): String? {
        if (!TextUtils.isEmpty(time))
            try {
                val sdf = SimpleDateFormat(format, Locale.CHINA)
                val sdfSource = SimpleDateFormat(sourceFormat, Locale.CHINA)
                val date = sdfSource.parse(time)
                return sdf.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        return null
    }

    fun compareNowTime(format: String, startTime: String): String {
        var start: Date? = null
        try {
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            start = sdf.parse(startTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (start == null) return startTime
        val s = isSameDate(start, Date())
        return if (!TextUtils.isEmpty(s)) {
            s!!
        } else startTime
    }

    private fun isSameDate(start: Date, end: Date): String? {
        val calendarStart = Calendar.getInstance()
        calendarStart.time = start
        val calendarEnd = Calendar.getInstance()
        calendarEnd.time = end
        val isSameYear = calendarStart.get(Calendar.YEAR) == calendarEnd.get(Calendar.YEAR)
        val isSameMonth = isSameYear && calendarStart.get(Calendar.MONTH) == calendarEnd.get(Calendar.MONTH)
        val isSameDate = isSameMonth && calendarStart.get(Calendar.DAY_OF_MONTH) == calendarEnd.get(Calendar.DAY_OF_MONTH)
        var hour: Any = calendarStart.get(Calendar.HOUR_OF_DAY)
        var minute: Any = calendarStart.get(Calendar.MINUTE)

        if ((minute as Int) < 10 && minute != 0) {
            minute = "0" + (minute)
        }

        if ((hour as Int) < 10 && hour != 0) {
            hour = "0" + (hour)
        }


        if (isSameDate) {
            return "今天" + " ${hour}:${minute}"
        }
        if (isSameMonth) {
            var between = calendarStart.get(Calendar.DAY_OF_MONTH) - calendarEnd.get(Calendar.DAY_OF_MONTH)
            if (between < 0) {
                between = -between
                if (between <= 1) {
                    return "昨天" + " ${hour}:${minute}"
                }
            }
        }
        return null
    }
}