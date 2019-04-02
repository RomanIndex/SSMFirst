package com.ssm.common.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class MathUtilTest {

    @Test
    /**
     * 拆、装箱和升、降级两者可以在同一条语句中进行，但是一定要 先 拆箱或装箱 再 升级或者降级。。。
     */
    public void int2Double(){
        int intA = 25;
        Integer integerA = Integer.valueOf(intA);
        //Double doubleA = integerA.doubleValue();//25.0
        Double doubleA = Integer.valueOf(intA).doubleValue();
        System.out.println(doubleA);
    }

    @Test
    public void isNumber(){
        System.out.println(MathUtil.isNumber("26"));
        System.out.println(MathUtil.isNumber("26.3"));//false
        System.out.println(MathUtil.isNumber("aa"));
        System.out.println("-------------------");
        System.out.println(MathUtil.isDouble("26"));//false
        System.out.println(MathUtil.isDouble("26.3"));
        System.out.println(MathUtil.isDouble("0.1"));
        System.out.println(MathUtil.isDouble("0.01"));//true
    }

    @Test
    public void endsWith0(){
        String regex = ".*\\.0+$";//.*不能少
        //String regex = "0+$";
        String a = "20.0";
        String aa = "20.00";
        String b = "0.26";
        String c = "26.02";

        Pattern pattern = Pattern.compile(regex);
        Matcher matchera = pattern.matcher(a);

        System.out.println(matchera.matches());
        System.out.println(a.matches(regex));
        System.out.println(aa.matches(regex));
        System.out.println(b.matches(regex));
        System.out.println(c.matches(regex));
    }
}
