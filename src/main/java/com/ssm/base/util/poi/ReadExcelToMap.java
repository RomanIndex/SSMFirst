package com.ssm.base.util.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.ssm.base.Enum.ExcelTableFieldEnum;
import com.ssm.base.entity.ExcelTableField;

public class ReadExcelToMap {

	/**
	 * 每一行构成一个map，key值是列标题，value是列值。没有值的单元格其value值为null
	 * 返回结果最外层的list对应一个excel文件，第二层的list对应一个sheet页，第三层的map对应sheet页中的一行
	 * 
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public static Map<String, List<ExcelTableField>> readExcelWithTitle(String filepath) throws Exception {
		String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(filepath);

			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new Exception("读取的不是excel文件");
			}

			Map<String, List<ExcelTableField>> sheetMapKeyName = new HashMap<>();//<sheet页名称， List<每行数据>>

			int sheetSize = wb.getNumberOfSheets();//有多少sheet页
			for (int i = 0; i < sheetSize; i++) {
				Sheet sheet = wb.getSheetAt(i);
				String sheetName = sheet.getSheetName();//获取工作表名称
				List<ExcelTableField> eachSheetListObj = new ArrayList<>();// ---对应sheet页的所有 行数据；每行可封装成一个obj，多行就是List<obj>
				
				List<String> firstRowTitle = new ArrayList<String>();// 放置所有 第一行 的标题
				List<String> usedEntityAttr = new ArrayList<>();// ---放置所有的，excel用到的 属性字段

				int rowSize = sheet.getLastRowNum() + 1;
				for (int j = 0; j < rowSize; j++) {// 遍历行
					Row row = sheet.getRow(j);
					if (row == null) {// 略过空行
						continue;
					}

					int cellSize = row.getLastCellNum();// 行中有多少个单元格，也就是有多少列
					if (j == 0) {//第一行是标题行

						for (int k = 0; k < cellSize; k++) {
							Cell cell = row.getCell(k);
							String chineseName = cell.toString();
							String entityAttr = changeToAttributeName(chineseName);
							firstRowTitle.add(chineseName);
							usedEntityAttr.add(entityAttr);
						}

					} else {//其他行是数据行
						// 拼成json格式，再转entity
						StringBuilder jsonStr = new StringBuilder();
						for (int k = 0; k < firstRowTitle.size(); k++) {
							Cell cell = row.getCell(k);
							String key = firstRowTitle.get(k);
							String attr = usedEntityAttr.get(k);// -----
							String value = null;
							if (cell != null) {
								value = cell.toString();
							}
							jsonStr.append("\"" + attr + "\":\"" + value + "\",");// ---全部当字符串处理
						}
						eachSheetListObj.add(strBuilterToObj(jsonStr));
					}
				}
				sheetMapKeyName.put(sheetName, eachSheetListObj);
			}
			//循环结束位置
			return sheetMapKeyName;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	private static ExcelTableField strBuilterToObj(StringBuilder jsonStr) {
		//System.out.println("开始的：" + jsonStr);
		StringBuilder delLastComma = jsonStr.deleteCharAt(jsonStr.length() - 1);
		//System.out.println("去掉末尾逗号的：" + delLastComma);
		String addBrace = "{" + delLastComma + "}";
		//System.out.println("加上首位大括号的：" + addBrace);
		JSONObject jsonObject = JSONObject.parseObject(addBrace);
		ExcelTableField obj = JSONObject.toJavaObject(jsonObject, ExcelTableField.class);
		return obj;
	}

	private static String changeToAttributeName(String chineseName) {
		String attrString = "";
		for (ExcelTableFieldEnum etfEnum : ExcelTableFieldEnum.values()) {
			if (etfEnum.getDescribeName().equals(chineseName)) {
				attrString = etfEnum.name();
			}
		}
		return attrString;
	}

}