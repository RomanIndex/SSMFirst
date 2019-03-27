package com.ssm.base.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	/**
	 * 可以 粗暴 是从excel表读入数据，但  没有  封装到 map或 实体类 里面
	 * 
	 * 参考：https://blog.csdn.net/Augus6/article/details/51463478
	 * 将excel表封装到实体类里面（数据库结构entity）
	 * @param clzz
	 * @return
	 */
	public static <T> Map<String, List<? extends T>> readExcel(String filepath, Class<?> clzz) {
		System.out.println(clzz);
		Map<String, List<? extends T>> map = new HashMap<String, List<? extends T>>();

		String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
		InputStream is = null;
		Workbook wb = null;

		try {
			is = new FileInputStream(filepath);;

			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new Exception("读取的不是excel文件");
			}

			int numberOfSheets = wb.getNumberOfSheets();
			for (int s = 0; s < numberOfSheets; s++) { // sheet工作表
				Sheet sheetAt = wb.getSheetAt(s);
				String sheetName = sheetAt.getSheetName(); // 获取工作表名称
				int rowsOfSheet = sheetAt.getPhysicalNumberOfRows(); // 获取当前Sheet的总行数
				System.out.println("【" + sheetName + "】Excel表的总行数:" + rowsOfSheet);
				int firstRowLine = 0;
				for (int r = 0; r < rowsOfSheet; r++) {
					Row row = sheetAt.getRow(r);
					if (row == null) {
						continue;
					} else {
						int rowNum = row.getRowNum();
						System.out.println("当前行:" + rowNum);
						if(rowNum == 0){
							firstRowLine = row.getPhysicalNumberOfCells();
						}
						System.out.println("当前行 非空总列数："+ row.getPhysicalNumberOfCells());
						for (int c = 0; c < firstRowLine; c++) { // 总列(格)
							Cell cell = row.getCell(c);
							if (cell == null) {
								System.out.print("--" + "\t");
								continue;
							} else {
								//int cellType = cell.getCellType();//（3.14）
								CellType cellType = cell.getCellTypeEnum();//（3.17）
								switch (cellType) {
									case _NONE: // 代表文本
										String UnknownValue = cell.getStringCellValue();
										System.out.print(UnknownValue + "Unknown" + "\t");
										break;
									case STRING: // 代表文本
										//case Cell.CELL_TYPE_STRING: // 代表文本
										String stringCellValue = cell.getStringCellValue();
										System.out.print("{" + stringCellValue +"}" + "\t");
										break;
									case BLANK: // 空白格
										//case Cell.CELL_TYPE_BLANK: // 空白格
										String blankValue = cell.getStringCellValue();
										System.out.print(blankValue + "--（"+ cell.getErrorCellValue() +"）--" + "\t");
										break;
									case BOOLEAN: // 布尔型
										//case Cell.CELL_TYPE_BOOLEAN: // 布尔型
										boolean booleanCellValue = cell.getBooleanCellValue();
										System.out.print(booleanCellValue + "\t");
										break;
									case NUMERIC: // 数字||日期
										//case Cell.CELL_TYPE_NUMERIC: // 数字||日期
										//判断是数字还是日期（org.apache.poi.ss.usermodel.DateUtil）
										boolean cellDateFormatted = DateUtil.isCellDateFormatted(cell);
										if (cellDateFormatted) {
											Date dateCellValue = cell.getDateCellValue();
											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
											System.out.print(sdf.format(dateCellValue) + "\t");
										} else {
											double numericCellValue = cell.getNumericCellValue();
											System.out.print(numericCellValue + "\t");
										}
										break;
									case ERROR: // 错误
										//case Cell.CELL_TYPE_ERROR: // 错误
										byte errorCellValue = cell.getErrorCellValue();
										System.out.print(errorCellValue + "error"+ "\t");
										break;
									case FORMULA: // 公式
										//case Cell.CELL_TYPE_FORMULA: // 公式
										int cachedFormulaResultType = cell.getCachedFormulaResultType();
										System.out.print(cachedFormulaResultType + "\t");
										break;
								}
							}
						}
						System.out.println(" \t ");
					}
					System.out.println("*********_*_*_*_*********");
				}
			}
			if (is != null) {
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}