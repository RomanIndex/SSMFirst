package com.ssm;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.base.builder.ConcreteBuilder;
import com.ssm.base.builder.JZZRedPacket;
import com.ssm.base.builder.RedPacket;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class BuilderTest {
	
	@Test
	public void test(){
		System.out.println("i an a  aa  aaa !!");
	}
	
	@Test
	public void builder(){
		RedPacket redPacket = ConcreteBuilder.getBulider().setPublisherName("壕发")
				.setPacketAmount(new BigDecimal("648"))
				.setPulishPacketTime(new Date())
				.setPacketType(1)
				.setAcceptName("王中啊啊啊")
				.setOpenPacketTime(new Date()).build();
		
		System.out.println(redPacket.getAcceptName());
	}
	
	@Test
	public void jzzBuilder(){
		JZZRedPacket redPacket = JZZRedPacket.getBulider().setPublisherName("壕发")
				.setPacketAmount(new BigDecimal("648"))
				.setPulishPacketTime(new Date())
				.setAcceptName(String.format("%tF%n", new Date()))
				.setOpenPacketTime(new Date()).build();
		
		System.out.println(redPacket.getAcceptName());
	}
	
	@Test
	public void dateFormat(){
		System.out.printf("%tF%n", new Date());
	}

}
