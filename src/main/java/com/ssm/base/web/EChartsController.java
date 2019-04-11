package com.ssm.base.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EChartsController {
	
	@RequestMapping(value = "admin/eCharts/index", method = RequestMethod.GET)
	public String dragIndex(Model model) {
		return "/admin/echart_demo.ftl";
	}
	
	/**
	 * 接收 行拖拽 传到后台的数据
	 */
	@ResponseBody
	@RequestMapping(value = "admin/eCharts/query", method = RequestMethod.GET)
	public String dragRow(String jsonData) {
		System.out.println(jsonData);
        return "success";
	}

}
