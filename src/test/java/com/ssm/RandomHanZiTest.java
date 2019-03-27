package com.ssm;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.base.service.RandomHanZiService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")

/**
 * 机内码、国际码是十六进制的，区位码是十进制的
 * 一般换算全部用十六进制
 * 不过特别注意：区位码从十进制转换为十六进制是两位两位分别转换的
 * ---------国际码 = 区位码（十六进制）+2020H
 * ---------机内码 = 国际码+8080H
 * ---------机内码 = （区位码（十六进制）+2020H）+8080H
 * 某汉字的 区位码 是2534。则25D=19H，34D=22H
 * 国际码 = 1922H + 2020H = 3952H
 * 机内码 = 3952H + 8080H = B9D2H
 *
 */

public class RandomHanZiTest {
	@Resource private RandomHanZiService hanZiService;
	
	@Test
	public void test(){
		System.out.println("hanzi can i do !!!");
	}
	
	@Test
	public void aa() throws UnsupportedEncodingException{
		for(int i = 0; i < 10; i++){
			System.out.print(getRandomChar());
		}
		System.out.println("----------（private static）方式一：------------");
		
		RandomHanZiService hanZiService = new RandomHanZiService();
		for(int i = 0; i < 10; i++){
			System.out.print(hanZiService.getRandomHan());
		}
		System.out.println("---------（外部类方法）方式二：-------------");
		
        for (int i = 0; i < 10; i++) {
            System.out.print(hanZiService.getRandomChar() + " ");
        }
        System.out.println("----------------------");
        
        for (int i = 0; i < 10; i++) {
            System.out.print(hanZiService.getRandomChar2(4) + " ");
        }
        
        System.out.println("----------------------");
        
        String  listHanZi = "李、王、张、刘、陈、杨、赵、黄、周、吴、朱".replace("、", "");
        char[] listChar = listHanZi.toCharArray();
        //汉字转 区位码
        for(int i = 0; i < listChar.length; i++){
        	String rets = "";
        	String oneStr = listChar[i] + "";
        	byte[] obs = oneStr.getBytes("GB2312");
        	for (int j = 0; j < obs.length; j++) {
                int a = Integer.parseInt(hanZiService.bytes2HexString(obs[j]), 16);
                rets += (a - 0x80 - 0x20) + "";
            }
        	//System.out.print(oneStr+ "："+rets +" ");
        	System.out.print(rets +",");
        }
        
        System.out.println("--------------------");
        
      	//汉字转 机内码
        for (int i = 0; i < listChar.length; i++) {
            String jnm = listChar[i] + "";
            System.out.print(jnm+"："+ RandomHanZiService.bytes2HexString(jnm) +" ");//调用外部类的public statis方法（静态方法）
        }
        
        System.out.println("--------------------");
        
        //C0EE CDF5 D5C5 C1F5 B3C2 D1EE D5D4 BBC6 D6DC CEE2 D6EC
        //区位码 转汉字
        System.out.println("区位码对应汉字："+hanZiService.codeToChinese("4585"));
	}

	private char getRandomChar() {
		double yy = Math.random() * (0x9fa5 - 0x4e00 + 1);
		System.out.print((int)yy + "--");
		return (char) (0x4e00 + (int) (yy));
	}

}