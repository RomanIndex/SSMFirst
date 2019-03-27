package com.ssm.base.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ssm.base.service.TestJava8Service;

@Controller
public class TestJava8Controller {
	@Autowired private TestJava8Service java8Service;

}
