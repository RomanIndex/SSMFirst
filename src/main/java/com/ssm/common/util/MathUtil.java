package com.ssm.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtil {

    public static boolean isNumber(String str) {
        String intRegex = "^\\d+$|-\\d+$";//就是判断是否为整数
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return str.matches(intRegex);
    }

    public static boolean isDouble(String str) {
        String intRegex = "\\d+\\.\\d+$|-\\d+\\.\\d+$";//判断是否为小数

        Pattern pattern = Pattern.compile(intRegex);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /* 计算两个数的百分比 */
    public static String calculatePercent(int sub, int mom) {
        if(mom <= 0){
            return "0%";
        }
        double percent = (double) sub / mom;
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(0);
        return nt.format(percent);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(long v1, long v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
