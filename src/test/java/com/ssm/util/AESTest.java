package com.ssm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ssm.base.util.AESUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class AESTest {

    @Test
    public void AESTest(){
        String message = "123456";
        String encryptStr = AESUtil.encrypt(message);
        System.out.println("加密前：" + message + "，加密后：" + encryptStr);

        String decryptStr = AESUtil.decrypt(encryptStr);
        System.out.println("解密前："+ encryptStr + "，解密后："+ decryptStr);

    }

}
