package com.ssm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.base.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class DateTest {
	
	@Test
	public void test(){
		System.out.println("i an a  aa  aaa !!");
	}
	
	@Test
	public void monthDay() throws ParseException{
		String strDate = "2019-01-27 10:23:56";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse(strDate);
		System.out.println(DateUtil.formatTime(date));
		//list.stream().forEach(item -> System.out.println(DateUtil.formatDateTime1(item)));
	}

}
