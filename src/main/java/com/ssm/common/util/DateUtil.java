package com.ssm.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final long ONE_DAY = 1000 * 24 * 60 * 60; //毫秒
    private static final long ONE_HOUR = 1000 * 60 * 60;
    private static final long ONE_MINUTE = 1000 * 60;

    //传入时间的年
    public static int getYear(Date date) {
        date = date == null ? Calendar.getInstance().getTime() : date;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    //传入时间的季度
    public static int getQuarter(Date date) {
        date = date == null ? Calendar.getInstance().getTime() : date;
        int month = getMonth(date);
        return (month-1)/3+1;
    }

    //传入时间的月份
    public static int getMonth(Date date) {
        date = date == null ? Calendar.getInstance().getTime() : date;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    //6小时后
    public static String afterSixHour(Date createTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(createTime);
        c.add(Calendar.HOUR, 6);
        Date untilDate = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(untilDate);
    }

    //传入的时间 是否 是当月时间，即是否是本月
    public static boolean checkThisMonth(Date createTime) {
        LocalDate now = ZonedDateTime.ofInstant(Calendar.getInstance().getTime().toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate create = ZonedDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return create.isEqual(now);
    }

    //当前时间的上一季度；分别为年、第几季度、季度的首月
    public static int[] getLastQuarter() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int quarter = (month - 1) / 3 + 1;
        //第一季度 的 上一季度，就是去年的4季度
        if(quarter == 1){
            year = year - 1;
            quarter = 4;
        }else{
            quarter = quarter - 1;
        }
        int firstMonth = quarter * 3 - 2;
        int[] ary = new int[3];
        ary[0] = year;
        ary[1] = quarter;
        ary[2] = firstMonth;
        return ary;
    }

    //获取上一个月
    public static int[] getLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        cal.set(year, month - 2, Calendar.DATE);//这里是 -2；

        int[] ary = new int[2];
        ary[0] = cal.get(Calendar.YEAR);
        ary[1] = cal.get(Calendar.MONTH ) + 1;

        return ary;
    }

    //判断两个时间相隔的小时数
    public static double getIntervalHour(Date beginDate, Date endDate) {
        long time = endDate.getTime() - beginDate.getTime();
        double hour = MathUtil.div(time, ONE_HOUR, 1);
        return hour;
    }
}
