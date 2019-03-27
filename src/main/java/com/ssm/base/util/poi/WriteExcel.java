package com.ssm.base.util.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//PS:可用于数据导出
public class WriteExcel {
	public static <T> void writeExcel(String savePath, List<T> list, LinkedHashMap<String,String> headMap, Class<T> clzz) {
		File file = new File(savePath);
		Workbook workbook = null;
		FileOutputStream fos = null;
		try {
			if (file.exists()) {
				String suffix = getSuffix(savePath);
				
				if (suffix.equalsIgnoreCase("XLSX")) {
					workbook = new XSSFWorkbook();
				} else if (suffix.equalsIgnoreCase("XLS")) {
					workbook = new HSSFWorkbook();
				} else {
					throw new Exception("当前文件不是excel文件");
				}
				
				fos = new FileOutputStream(file);//建议：先创建workbook，再创建fileoutstream //(另一个程序正在使用此文件，进程无法访问。)
				
				Sheet sheet = workbook.createSheet("是excel名吗");//生成sheet页，括号可设置 sheet页名，但别以？结尾
		        //设置宽度和高度
		        //row.setHeightInPoints(30);//设置行的高度
		        sheet.setColumnWidth(0, 20 * 256);//设置列的宽度
				
		        Field[] declaredFields = clzz.getDeclaredFields();
				Row row = sheet.createRow(0);
				
				// 设置title（只导出 传入 的字段）
				int headIndex = -1;
				for(Entry<String, String> entry : headMap.entrySet()){
					String key = entry.getKey();//对应 实体类 的属性
					String val = entry.getValue();
					headIndex++;
					for (int m = 0; m < declaredFields.length; m++) {
						Field field = declaredFields[m];
						field.setAccessible(true);
						String name = field.getName();
						if(name.equals(key)){
							Cell cell = row.createCell(headIndex);
							cell.setCellType(CellType.STRING);
							cell.setCellValue(val);//转换成导出字段的中文名，且可以 决定 需要导出的字段
							//为表头设置样式
							cell.setCellStyle(setTitleCellStyle(workbook));
							//设置 标题行 的高度 
							row.setHeightInPoints(30);
						}else{
							continue;
						}
					}
				}
				
				// 数据
				for (int i = 0; i < list.size(); i++) {
					T instance = list.get(i);
					row = sheet.createRow(i + 1);
					int headMapIndex = -1;
					for(Entry<String, String> entry : headMap.entrySet()){
						headMapIndex++;
						for (int j = 0; j < declaredFields.length; j++) {
							Field field = declaredFields[j];
							field.setAccessible(true);
							String name = field.getName();
							if(name.equals(entry.getKey())){
								Object value = field.get(instance);
								value = value == null ? "--": value;
								String fieldTypeName = field.getGenericType().getTypeName();
								field.setAccessible(true);
								Cell cell = row.createCell(headMapIndex);
								switch (fieldTypeName) {
								case "long":
									double d = Double.valueOf(value.toString());
									cell.setCellValue(d);
									break;
								case "float":
									CellStyle floatStyle = workbook.createCellStyle();
									short format = workbook.createDataFormat().getFormat(".00");// 保留2位精度
									floatStyle.setDataFormat(format);
									double d1 = Double.parseDouble(String.valueOf(value));
									cell.setCellStyle(floatStyle);
									cell.setCellValue(d1);
									break;
								case "int":
									double d2 = Double.parseDouble(String.valueOf(value));
									cell.setCellValue(d2);
									break;
								case "java.util.Date":
									CellStyle dateStyle = workbook.createCellStyle();
									
									Font dateFont = workbook.createFont();
									dateFont.setFontName("华文楷体");//设置字体名称
									//dateFont.setFontHeightInPoints((short)11);//设置字号
									dateFont.setItalic(false);//设置是否为斜体
									dateFont.setBold(true);//设置是否加粗
									dateFont.setColor(IndexedColors.RED.index);//设置字体颜色
									dateStyle.setFont(dateFont);
									
									short df = workbook.createDataFormat().getFormat("yyyy-mm-dd");
									dateStyle.setDataFormat(df);
									cell.setCellStyle(dateStyle);
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String format2 = sdf.format(value);
									Date date = sdf.parse(format2);
									cell.setCellValue(date);
									break;
								case "java.lang.String":
									cell.setCellValue(value.toString());
									break;
								}
							}else{
								continue;
							}
						}
					}
					
				}
				
				workbook.write(fos);//千万别再循环体内输出
				
			} else {
				
				if (file.createNewFile()) {
					writeExcel(savePath, list, headMap, clzz);
				} else {
					System.out.println("创建Excel表格失败!");
				}
			}
			
			if (fos != null) {
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static CellStyle setTitleCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
        //加边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中 
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("华文楷体");//设置字体名称
        font.setFontHeightInPoints((short)14);//设置字号
        font.setItalic(false);//设置是否为斜体
        font.setBold(true);//设置是否加粗
        font.setColor(IndexedColors.RED.index);//设置字体颜色
        cellStyle.setFont(font);
        //设置背景
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        
		return cellStyle;
	}

	private static String getSuffix(String path) {
		String substring = path.substring(path.lastIndexOf(".") + 1);
		return substring;
	}

}