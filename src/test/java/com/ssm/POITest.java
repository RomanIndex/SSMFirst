package com.ssm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.ssm.base.view.Result;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.ssm.base.entity.Account;
import com.ssm.base.entity.ExcelTableField;
import com.ssm.base.service.CreateOracleTableSqlService;
import com.ssm.base.service.ExcelService;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class POITest {
	@Resource private ExcelService excelService;
	@Resource private CreateOracleTableSqlService createOracleTableSqlService;
	
	@Test
	/**
	 * 导入（简单通用）
	 *
	 * 目前只做单页sheet的，因为 入参 就一个class类，返回对应list集合
	 *
	 * 考虑到后台上传的情形，要兼顾 流 的处理
	 */
	public void readExcel(){
		String path = "D:\\LocalPicDev\\实体类account模拟数据.xlsx";
		//String path = "D:\\LocalPicDev\\OTC\\OTC答题码.xls";
		System.out.println("文件路径："+ path);
		File file = new File(path);
		try {
			FileInputStream input = new FileInputStream(file);
			MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			Result result = excelService.readExcel(multipartFile, Account.class);
			System.out.println(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * 导入（表结构、生成建表SQL）
	 */
	public void readExcelToMap() throws Exception{
		/*支持多sheet生成的excel，【sheet命名】不要有中文，是 对应 【数据库表】的名称的【驼峰名字】的*/
		String path = "D:\\LocalPicDev\\SSM基本表结构（待导入建表）.xlsx";
		Map<String, List<ExcelTableField>> xxMap = excelService.readExcelToMap(path);
		System.out.println(xxMap);
		//根据 excel 导入的 表结构 生成 对应的表生成sql语句
		createOracleTableSqlService.createSql(xxMap);
	}
	
	@Test
	/**
	 * 导出
	 */
	public void writeExcel(){
		String savePath = "D:\\LocalPicDev\\导出文件account数据.xlsx";//文件存放路径
		List<Account> list = createList();
		//实体类打@标签？不好，因为防止不同导出表格显示的 标题字段 不一致
		LinkedHashMap<String, String> headMap = new LinkedHashMap<>();
		headMap.put("id", "唯一标识");
		headMap.put("name", "姓名");
		headMap.put("mobile", "手机号");
		headMap.put("email", "邮箱（故意标题很长啊啊啊啊啊啊啊啊啊啊啊啊啊）");
		headMap.put("createTime", "创建时间");
		//根据headMap定义的字段 导出数据（支持样式的定义）（利用java的反射实现）
		String txt = excelService.writeExcel(savePath, list, headMap, Account.class);
		System.out.println(txt);
	}

	//------------------生成 模拟数据------------------------
	private List<Account> createList() {
		List<Account> list = new ArrayList<>();
		for(int i = 0; i < 3; i ++){
			Account obj = new Account();
			obj.setId("id___"+i);
			obj.setName("name__"+ (i*i));
			obj.setMobile((123456 * i) + "666");
			obj.setCreateTime(new Date());
			list.add(obj);
		}
		return list;
	}
	
	@Test
	public void entityJson(){
		ExcelTableField obj = createObj();
		System.out.println("obj："+ obj);
		System.out.println("toString()："+ obj.toString());
		//根据 表结构 导入的 逻辑处理中，也用到了com.alibaba.fastjson相关包
		System.out.println("toJSONString："+ JSONObject.toJSONString(obj));
		//{"name":ID,"column":ID,"type":VARCHAR2(32),"primary":TRUE,"ableNull":FALSE,"defaultValue":sys_guid(),"remark":id}
	}
	
	private ExcelTableField createObj() {
		ExcelTableField obj = new ExcelTableField();
		obj.setName("中文");
		obj.setColumn("zhong_wen");
		obj.setRemark("1：有效；0：无效");
		return obj;
	}

}