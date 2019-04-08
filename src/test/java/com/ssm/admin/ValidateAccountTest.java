package com.ssm.admin;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.admin.service.impl.AccountServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class ValidateAccountTest {
	@Autowired private AccountServiceImpl accountServiceImpl;
	
}