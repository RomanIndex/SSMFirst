package com.ssm.common.util;

import com.ssm.common.util.AESUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class AESTest {

    @Test
    public void AESTest(){
        String message = "besb4m5";
        String encryptStr = AESUtil.encrypt(message);
        System.out.println("加密前：" + message + "，加密后：" + encryptStr);

        String decryptStr = AESUtil.decrypt(encryptStr);
        System.out.println("解密前："+ encryptStr + "，解密后："+ decryptStr);

    }

}
