package com.ssm.common.util;

public class StringUtil {

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
