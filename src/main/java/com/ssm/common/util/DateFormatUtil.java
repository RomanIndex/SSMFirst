package com.ssm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");
    private static final long ONE_MINUTE = 60;//单位秒（s）
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;

    /**
     * 格式化时间
     * @return String  "yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDate(Date date) {
        if(date != null) {
            return sdf.format(date);
        }else{
            return "";
        }
    }

    /**
     * 格式化日期
     *
     * @return String  "yyyy-MM-dd"
     */
    public static String formatDateYMD(Date date) {
        if(date != null) {
            return sdfYmd.format(date);
        }else{
            return "";
        }
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return String
     */
    public static String formatTime(Date date) {
        if(date == null){
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;

        if (ago <= ONE_HOUR){
            long theTime = ago / ONE_MINUTE;
            if(theTime == 0){
                return "刚刚";
            }
            return theTime + "分钟前";

        }else if(ago <= ONE_DAY){
            return ago / ONE_HOUR + "小时前";

        }else if (ago <= ONE_DAY * 2){
            return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        }else if (ago <= ONE_DAY * 3){
            return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        }else{
            return sdfYmd.format(date);
        }
    }

    /**
     * 获取一个日期和当前日期相差的天数
     *
     * @param date
     * @return int
     */
    public static int daysBetween(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date dNow = sdf.parse(sdf.format(cal.getTime()));   //当前时间
        cal.setTime(dNow);
        long nowTime = cal.getTimeInMillis();
        date = sdf.parse(sdf.format(date));
        cal.setTime(date);
        long theTime = cal.getTimeInMillis();
        long between_days= (nowTime-theTime) /(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
