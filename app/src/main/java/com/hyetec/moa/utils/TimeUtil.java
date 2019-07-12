package com.hyetec.moa.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    private static long lastClickTime=0;

    public static boolean isFastDoubleClick(int i)
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if(timeD>=0 && timeD<=i){
            return true;
        }else {
            lastClickTime = time;
            return false;
        }
    }
    public static String getTime(String dateTime){
        if(dateTime==null){
            return null;
        }
        if(dateTime.length()>16){
            return dateTime.substring(10, 16);
        }else{
            return dateTime;
        }
    }
    public static boolean isFastDoubleLongClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 1000) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    public static String clearTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        if (dateTime.length() > 10) {
            return dateTime.substring(0, 10);
        } else {
            return dateTime;
        }

    }
    /**
     * 获取当前的时间戳
     */
    public static long getTimestamp(){
        long millis = System.currentTimeMillis();
        return millis;
    }

    /**
     * 获取当前的时间戳
     */
    public static String getTime(){
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");
        String time =df.format(new Date());
        return time;
    }


    public static String clearSecond(String dateTime)
    {
        if(dateTime==null){
            return null;
        }
        if(dateTime.length()>10){
            return dateTime.substring(0, 10);
        }else{
            return dateTime;
        }

    }

    public static String clearData(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        if (dateTime.length() > 10) {
            return dateTime.substring(0, 10);
        } else {
            return dateTime;
        }

    }

    /**
     * @param dateTime
     * @return
     * @Title:
     * @Description: 格式日期格式，去掉时间
     */
    public static String formatDate(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        if (dateTime.length() > 10) {
            return dateTime.substring(0, 10);
        }
        return dateTime;

    }

    public static String getSQLDateTimeString(Date paramDate, String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
        if (paramDate == null) {
            paramDate = new Date();
        }
        return localSimpleDateFormat.format(paramDate);
    }

    public static long getDateTimeString2Long(String paramString1, String paramString2) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2, Locale.SIMPLIFIED_CHINESE);
        try {
            Date localDate = localSimpleDateFormat.parse(paramString1);
            return localDate.getTime();
        } catch (ParseException localParseException) {
        }
        return 0L;
    }

    public static Date getDateFromSqlDateTimeString(String paramString) {
        try {
            if (TextUtils.isEmpty(paramString)) {
                throw new Exception("date is Null");
            }
        } catch (ParseException localParseException) {
            Date localDate = null;
            try {
                localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(paramString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return localDate;
        } catch (Exception localException) {
        }
        return new Date();
    }

    /**
     * 格式化日期格式
     *
     * @param calendar
     * @param template
     * @return
     */
    public static String formatDateTimeString(Calendar calendar, String template) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(template, Locale.SIMPLIFIED_CHINESE);
        return localSimpleDateFormat.format(calendar.getTime());
    }

    /**
     * 格式化日期格式(年月日)
     *
     * @param year
     * @param month
     * @param day
     * @param template
     * @return
     */
    public static String formatDateTimeString(int year, int month, int day, String template) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return formatDateTimeString(calendar, template);
    }

    /**
     * @return
     * @Title:
     * @Description: 获取今天日期字符串
     */
    public static String getToadyString() {
        return formatDateTimeString(Calendar.getInstance(), "yyyy-MM-dd");
    }

    /**
     * @return
     * @Title:
     * @Description: 获取今天日期字符串
     */
    public static String getNowTimeString() {
        return formatDateTimeString(Calendar.getInstance(), "yyyy-MM-dd HH:mm");
    }

    /**
     * String to date
     */
    public static Date getDateByDateTimeString(String dateStr, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    public static String getTitles(int day) {

        String mYear; // 当前年
        String mMonth; // 月
        String mDay;
        int HH;
        int mm;
        int current_day;
        int current_month;
        int current_year;

        final Calendar c = Calendar.getInstance();
        current_day = c.get(Calendar.DAY_OF_MONTH);
        current_month = c.get(Calendar.MONTH);
        current_year = c.get(Calendar.YEAR);
        HH= c.get(Calendar.HOUR);
        mm= c.get(Calendar.MINUTE);
        c.clear();//记住一定要clear一次

        c.set(Calendar.MONTH, current_month);
        c.set(Calendar.DAY_OF_MONTH, current_day);
        c.set(Calendar.YEAR, current_year);
        c.add(Calendar.DATE, day);//j记住是DATE
        c.add(Calendar.HOUR, HH);
        c.add(Calendar.MINUTE, mm);
        return formatDateTimeString(c, "yyyy-MM-dd HH:mm");

//        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
//        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
//        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
//        String date = mYear + "-" + mMonth + "-" + mDay + " "+HHmm;

//        return date;
    }


    public static String stringToStr(String dateStr) {

        dateStr = dateStr.replace("年", "-").replace("月", "-").replace("日", "");

        return dateStr;
    }

    public static String formatDate(int dayOrMonth) {
        if (dayOrMonth < 10) {
            return "0" + dayOrMonth;
        }
        return String.valueOf(dayOrMonth);
    }

    public static String getDateTimeLong2String(long paramLong, String paramString) {
        return new SimpleDateFormat(paramString).format(Long.valueOf(paramLong));
    }

    //计算时间间隔
    public static String getInterval(String startTime, String endTime) { //传入的时间格式必须类似于2012-8-21 17:53:20这样的格式
        String interval = null;
        if (TextUtils.isEmpty(startTime)) {
            return null;
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date d1 = (Date) sd.parse(startTime);
            Date d2 = null;
            if (TextUtils.isEmpty(endTime)) {
                d2 = new Date();
            } else {
                d2 = (Date) sd.parse(endTime);
            }

            //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
            long time = Math.abs(d2.getTime() - d1.getTime());// 得出的时间间隔是毫秒

            if (time / 3600000 < 24 && time / 3600000 >= 0) {
                //如果时间间隔小于24小时则显示多少小时前
                int h = (int) (time / 3600000);//得出的时间间隔的单位是小时
                int m = (int) ((time % 3600000) / 60000);//得出的时间间隔的单位是分钟


                if (h == 0 && m == 0) {
                    interval = "0";
                } else {
                    interval = (h > 0 ? h + "小时" : "") + (m > 0 ? m + "分钟" : "");
                }
            } else if (time / 60000 < 60 && time / 60000 > 0) {
                //如果时间间隔小于60分钟则显示多少分钟前
                int m = (int) ((time % 3600000) / 60000);//得出的时间间隔的单位是分钟
                interval = m + "分钟";

            } else if (time / 1000 < 60 && time / 1000 > 0) {
                //如果时间间隔小于60秒则显示多少秒前
                int se = (int) ((time % 60000) / 1000);
                interval = se + "秒";

            } else {
                //大于24小时，则显示正常的时间，但是不显示秒
                int d = (int) (time / 86400000);//得出的时间间隔的单位是天
                int h = (int) ((time % 86400000) / 3600000);//得出的时间间隔的单位是小时
                int m = (int) ((time % 3600000) / 60000);//得出的时间间隔的单位是分钟

                interval = d + "天" + (h > 0 ? h + "小时" : "") + (m > 0 ? m + "分钟" : "");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

}
