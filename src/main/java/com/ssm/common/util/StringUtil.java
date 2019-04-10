package com.ssm.common.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

public class StringUtil {

    public static String UTF8(String para){
        try {
            para = para == null ? "" : para;
            return new String(para.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isMobile(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (StringUtils.isEmpty(mobiles)) {
            return false;
        } else{
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 处理字符串  如：  abc_dex ---> abcDex
     * @param str
     * @return
     */
    public static  String removeLine(String str){
        if(null != str && str.contains("_")){
            int i = str.indexOf("_");
            char ch = str.charAt(i+1);
            char newCh = (ch+"").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i+1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }

    /**
     * 驼峰 转 下划线
     * @param hump
     * @return
     */
    public static String hump2underline(String hump) {
        StringBuilder underline = new StringBuilder();
        if (hump != null && hump.length() > 0) {
            // 将第一个字符处理成大写
            underline.append(hump.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < hump.length(); i++) {
                String s = hump.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    underline.append("_");
                }
                // 其他字符直接转成大写
                underline.append(s.toUpperCase());
            }
        }

        return underline.toString();
    }
}
