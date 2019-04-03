package com.ssm.common.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class StringUtilTest {

    @Test
    public void removeLine(){
        System.out.println(StringUtil.removeLine("abg_dwr"));
        System.out.println(StringUtil.removeLine("ab_wr"));
        System.out.println(StringUtil.removeLine("abgwr"));
        System.out.println(StringUtil.removeLine(null));
    }

    @Test
    public void isMobile(){
        System.out.println(StringUtil.isMobile("13699794520"));
        System.out.println(StringUtil.isMobile("1369979452"));
        System.out.println(StringUtil.isMobile(null));
    }
}
