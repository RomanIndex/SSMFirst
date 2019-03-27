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

import com.ssm.base.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class AESTest {

    @Test
    public void test(){
        System.out.println("i an a  aa  aaa !!");
    }

    @Test
    public void AESTest(){
        String message = "123456";
        String encryptStr = AESUtil.encrypt(message);
        System.out.println("加密前：" + message + "，加密后：" + encryptStr);

        String decryptStr = AESUtil.decrypt(encryptStr);
        System.out.println("解密前："+ encryptStr + "，解密后："+ decryptStr);

    }

}
