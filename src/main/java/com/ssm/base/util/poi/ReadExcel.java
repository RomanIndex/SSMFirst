package com.ssm.base.util.poi;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ssm.base.view.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.Object;
import org.springframework.web.multipart.MultipartFile;

public class ReadExcel {

	/**
	 * 消费者使用<? super T>,表示包括T在内的任何T的父类,即你想把T类型的元素加入到列表中,因此你不能保证从中读取到的元素的类型
	 *
	 * 生产者使用<? extends T>,表示包括T在内的任何T的子类, 即你想从列表中读取T类型的元素; 因此你不能往该列表中添加任何元素
	 *
	 * PECS原则：https://www.cnblogs.com/lucky_dai/p/5485421.html
	 *
	 * ----------------------------------------------------------------------------
	 *
	 * 参考：https://blog.csdn.net/Augus6/article/details/51463478
	 * 将excel表封装到实体类里面（数据库结构entity）
	 * @param clzz
	 * @return
	 */
	//public static <T> Map<String, List<? extends T>> readExcel(MultipartFile multipartFile, Class<?> clzz) {
	public static Result<?> readExcel(MultipartFile multipartFile, Class<?> clzz) {
		Result result = new Result<>();
		InputStream is = null;//InputStream is = new FileInputStream("文件路径");
		Workbook wb = null;
		String fileName = multipartFile.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		//Map<String, List<? extends T>> map = new HashMap<String, List<? extends T>>();
		//Map<String, List<? super T>> map = new HashMap<String, List<? super T>>();
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();

		System.out.println(clzz);

		try {
			is = multipartFile.getInputStream();

			if(fileType.equals("xls")){
				wb = new HSSFWorkbook(is);
			}else if (fileType.equals("xlsx")){
				wb = new XSSFWorkbook(is);
			}else{
				throw new Exception("读取的不是excel文件");
			}

			int numberOfSheets = wb.getNumberOfSheets();

			for (int s = 0; s < numberOfSheets; s++) { // sheet工作表
				Sheet sheetAt = wb.getSheetAt(s);
				String sheetName = sheetAt.getSheetName(); // 获取工作表名称
				int rowsOfSheet = sheetAt.getPhysicalNumberOfRows(); // 获取当前Sheet的总行数
				System.out.println("导入的Excel表第 "+ s +" sheet页【" + sheetName + "】，总共有 " + rowsOfSheet +" 行");
				System.out.println("-------------【" + sheetName + "】，每一行 对应的 类： " + clzz.getName());

				List<String> referList = new ArrayList<>();//通常是 表头 对应 类的字段名，也可以写外面方法获取
				//List<String> staticList = asList("JDK6", "JDK8", "JDK10");//asList 是 Arrays 的静态方法，staticList不能添加、删除等操作
				referList = new ArrayList<>(Arrays.asList("1", "2", "3"));

				List<Object> eachList = sheetData2ListObj(sheetAt, clzz, referList);//关键，sheet表数据 转 对应类的集合
				//List<T> eachList = sheetData2ListObj(sheetAt, clzz);//没必要，直接指定Object就好
				map.put(clzz.getName(), eachList);
			}

			if (is != null) {
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Result<>(Result.SUCCESS, "", null, map);
	}

	/**
	 * 将一个sheet转换为一行List<Object>
	 * @param sheetAt
	 * @param clzz
	 * @param referList
	 * @return
	 */
	private static List<Object> sheetData2ListObj(Sheet sheetAt, Class<?> clzz, List<String> referList) {
		List<Object> list = new ArrayList<>();

		//获取当前Sheet的总行数，即返回集合的size()
		int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();

		for (int r = 0; r < rowsOfSheet; r++) {
			/**
			 * 1、为每一行new一个对象
			 * 2-1、遍历行的每一列，取值（判断字段类型）
			 * 2-2、根据 列 与 对象属性值 的对应关系，将 取的值 set 到 对象属性里面（**）
			 * 3、将set满值的对象，add到返回的List集合
			 */
			Row row = sheetAt.getRow(r);
			if (row == null) {
				//要判断空的行，继续
				continue;
			} else {
				int rowSeq = row.getRowNum();
				int notNullLine = row.getPhysicalNumberOfCells();
				System.out.println("当前行:" + rowSeq +"，有"+ notNullLine +"列值（空值不算）");

				//默认第一行是标题行，不封装进对象
				if(rowSeq != 0){
					Object object = row2Object(row, clzz, referList);//将每一行转为一个对象
					list.add(object);
				}
			}
			System.out.println(" \t ");
		}

		return list;
	}

	/**
	 * 将 一行 转换为 一个对象
	 * @param row
	 * @param clzz
	 * @param referList
	 * @return
	 */
	private static Object row2Object(Row row, Class<?> clzz, List<String> referList) {
		Object object = null;//（？）根据clzz创建一个实例

		int notNullLine = row.getPhysicalNumberOfCells();

		for (int c = 0; c < notNullLine; c++) { // 总列(格)
			Cell cell = row.getCell(c);
			String cellStringVal = getStringValue(cell);;//这里只能全部当字符处理

			System.out.println("第"+ c +"列取出的string是"+ cellStringVal+"，对应 类 的字段"+ referList.get(c));

			//（？？）往对象实例set cell的值
		}

		return object;
	}

	/**
	 * 获取每一个cell的值
	 * @param cell
	 * @return
	 */
	private static String getStringValue(Cell cell) {
		String cellStringVal = "";

		if (cell == null) {
			return cellStringVal;
		}

		//int cellType = cell.getCellType();//（3.14）
		CellType cellType = cell.getCellTypeEnum();//（3.17）
		switch (cellType) {
			case _NONE: // 代表文本
				String UnknownValue = cell.getStringCellValue();
				System.out.print(UnknownValue + "Unknown" + "\t");
				break;
			case STRING: // 代表文本
				//case Cell.CELL_TYPE_STRING: // 代表文本
				//String cellvalue = cell.getRichStringCellValue().getString();
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

				/*String cellvalue = "";
				if(String.valueOf(cell.getNumericCellValue()).indexOf("E") == -1){
					cellvalue =  String.valueOf(cell.getNumericCellValue());
				}else {
					cellvalue =  new DecimalFormat("#").format(cell.getNumericCellValue());
				}*/

				break;
			case ERROR: // 错误
				//case Cell.CELL_TYPE_ERROR: // 错误
				byte errorCellValue = cell.getErrorCellValue();
				System.out.print(errorCellValue + "error"+ "\t");
				break;
			case FORMULA: // 公式
				//case Cell.CELL_TYPE_FORMULA: // 公式
				String cachedFormulaResultType = cell.getCellFormula();
				//int cachedFormulaResultType = cell.getCachedFormulaResultType();
				System.out.print(cachedFormulaResultType + "\t");
				break;
		}

		return cellStringVal;
	}

}