package com.ximalife.library.util;


/**
 * 获取当前时间戳的方法：
 * 1.System.currentTimeMillis()
 * 2.Calendar.getInstance()
 */


import android.content.Context;

import com.ximalife.library.R;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by xing 2017/6/14.
 * 时间工具类：时间戳转化为时间  比较时间相差多少
 * 修改SimpleDateFormat里的格式，可转换成相应格式时间
 */

public class TimeUtil {


    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }


    public static String dateConversion(long date, String fromat) {
        try {
            return new SimpleDateFormat(fromat).format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long StringToLong(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //字符串转时间戳
    public static String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = formatter.parse(timeString);
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }


//    public static String getCurrentTimeString(Context context, long time) {
//        if (isToday(new Date(time))) {
//            return context.getResources().getString(R.string.today);
//        } else if (isYesterday(time)) {
//            return context.getResources().getString(R.string.yesterday);
////        } else if (isTomorrowday(time)) {
////            return "明天";
//        } else if (isThisWeek(new Date(time))) {
//            return context.getResources().getString(R.string.this_week);
//        } else if (isThisMonth(new Date(time))) {
//            return context.getResources().getString(R.string.this_month);
//        } else if (isThisYear(new Date(time))) {
//            return context.getResources().getString(R.string.this_year);
//        }
//        return context.getResources().getString(R.string.today);
//    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    //判断选择的日期是否是今天
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    //判断选择的日期是否是本年
    public static boolean isThisYear(Date time) {
        return isThisTime(time, "yyyy");
    }


    private static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }


    public static boolean isTomorrowday(long time) {
        boolean isTomorrowday = false;
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(sdf.format(new Date()));
            if (time > date.getTime() && time > (date.getTime() - 24 * 60 * 60 * 1000)) {
                isTomorrowday = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isTomorrowday;
    }


    public static boolean isYesterday(long time) {
        boolean isYesterday = false;
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(sdf.format(new Date()));
            if (time < date.getTime() && time > (date.getTime() - 24 * 60 * 60 * 1000)) {
                isYesterday = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isYesterday;
    }

}
