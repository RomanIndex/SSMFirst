package com.ssm.base.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssm.base.entity.ExcelTableField;
import com.ssm.base.util.poi.ReadExcel;
import com.ssm.base.util.poi.ReadExcelToMap;
import com.ssm.base.util.poi.WriteExcel;

@Service
public class ExcelService {
	
	/**
	 *  导入
	 *  简单 但是 根据excel表 字段 类型 精准读取
	 * @param path
	 * @param clzz
	 * @return
	 */
	public String readExcel(String path, Class<?> clzz) {
		Map<String, List<? extends Object>> map = ReadExcel.readExcel(path, clzz);
		
		return map.toString();
	}
	
	/**
	 * 导入
	 * @param path 表结构 excel文件的存放路径
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<ExcelTableField>> readExcelToMap(String path) throws Exception {
		Map<String, List<ExcelTableField>> xxList = ReadExcelToMap.readExcelWithTitle(path);
		
		return xxList;
	}
	
	/**
	 *  导出
	 * @param savePath 导出文件存放路径
	 * @param list 要导出的数据集合
	 * @param headMap 需要要导出的字段
	 * @param clzz list里面的 类
	 * @return
	 */
	public <T> String writeExcel(String savePath, List<T> list, LinkedHashMap<String,String> headMap, Class<T> clzz) {
		WriteExcel.writeExcel(savePath, list, headMap, clzz);
		
		return "yyyyy";
	}
	
}
