package com.ssm.base.util.poi;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.ssm.admin.entity.SsmModule;
import com.ssm.base.service.ReflectFieldService;
import com.ssm.base.util.PropertyUtil;
import com.ssm.base.view.Result;
import com.ssm.common.entity.ComStudent;
import com.ssm.common.util.MathUtil;
import com.ssm.common.util.StringUtil;
import com.sun.deploy.config.Config;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	 * @param groups
	 * @return
	 */
	//public static <T> Map<String, List<? extends T>> readExcel(MultipartFile multipartFile, Class<?> clzz) {
	public static Result<?> readExcel(MultipartFile multipartFile, Class<?>... groups) {
		Result result = new Result<>();
		InputStream is = null;//InputStream is = new FileInputStream("文件路径");
		Workbook wb = null;
		String fileName = multipartFile.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		//Map<String, List<? extends T>> map = new HashMap<String, List<? extends T>>();
		//Map<String, List<? super T>> map = new HashMap<String, List<? super T>>();
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();

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

			if(numberOfSheets != groups.length){
                return new Result<>(Result.FAIL, "sheet页数 与 接收类的个数 不一致！", null, null);
            }

			for (int s = 0; s < numberOfSheets; s++) { // sheet工作表
				Class clzz = groups[s];
				String classFullName = clzz.getName();
				Sheet sheetAt = wb.getSheetAt(s);
				String sheetName = sheetAt.getSheetName(); // 获取工作表名称
				int rowsOfSheet = sheetAt.getPhysicalNumberOfRows(); // 获取当前Sheet的总行数

				System.out.println("导入的Excel表第 "+ s +" sheet页【" + sheetName + "】，总共有 " + rowsOfSheet +" 行");
				System.out.println("每一行 封装的 类》》" + clzz + "，.getName() = "+ classFullName);

				List<String> referList = getReferListByClass(clzz);

				List<Object> eachList = sheetData2ListObj(sheetAt, clzz, referList);//关键，sheet表数据 转 对应类的集合
				//List<T> eachList = sheetData2ListObj(sheetAt, clzz);//没必要，直接指定Object就好

				String className = classFullName.substring(classFullName.lastIndexOf(".") + 1, classFullName.length());
				map.put(className, eachList);
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
	 * 根据 类 获取 对应存在property里面的 属性集合
	 * @param clzz
	 * @return
	 */
	private static List<String> getReferListByClass(Class<?> clzz) {
		String strWithComma = "";
		if(clzz == ComStudent.class){
			strWithComma = PropertyUtil.getProperty(PropertyUtil.IMPORT_EXCEL_STUDENT);
		}else if(clzz == SsmModule.class){
			strWithComma = PropertyUtil.getProperty(PropertyUtil.IMPORT_EXCEL_MODULE);
		}
		return Splitter.on(",").trimResults().splitToList(strWithComma);
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
				int notNullLine = row.getPhysicalNumberOfCells();//应该和referList.size()相等就正常
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
        Object obj = null;

        try {
            obj = clzz.newInstance();//根据clzz创建一个实例

			int referSize = referList.size();

            //每一行的总列（格）
            for (int c = 0; c < referSize; c++) {
                Cell cell = row.getCell(c);
                Object cellVal = getCellValue(cell);//----cell值支持多种（String，Integer，Double，布尔）
                String fieldName = referList.get(c);//可以从referList里取
                System.out.println("《《 第"+ c +"列，类 的字段"+ fieldName + " = "+ cellVal);
                Class fieldClazz = clzz.getDeclaredField(fieldName).getType();

                Object fieldTypeValue = getFieldTypeValue(fieldClazz, cellVal);//获取和field类型对应的set的值

                ReflectFieldService.setValue(obj, obj.getClass(), fieldName, fieldClazz, fieldTypeValue);
            }

            System.out.println(obj +"》》"+ JSON.toJSON(obj));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

		return obj;
	}

	//暂时只需要处理这三种数值类型的
	private static Object getFieldTypeValue(Class fieldClazz, Object cellVal) {
		String methodName = null;

		if(fieldClazz == int.class){
			methodName = "intValue";
		}else if(fieldClazz == short.class){
			methodName = "shortValue";
		}else if(fieldClazz == double.class){
			methodName = "doubleValue";
		}else if(fieldClazz == boolean.class){
			//布尔类型，特殊处理
			int v = Integer.valueOf(cellVal.toString()).intValue();
			return v == 1 ? true : false;
		}else{
			return cellVal;
		}

		Object fieldVal = null;

		try {
			Method method = cellVal.getClass().getDeclaredMethod(methodName);
			fieldVal = method.invoke(cellVal);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return fieldVal;
	}

	/**
	 * 获取每一个cell的值
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
        Object cellVal = null;

		if (cell == null) {
			return null;
		}

		//int cellType = cell.getCellType();//（3.14）
		CellType cellType = cell.getCellTypeEnum();//（3.17）
		switch (cellType) {
			case _NONE: // 代表文本
				String text = cell.getStringCellValue();
                cellVal = text;
				break;
			case STRING: // 代表文本
				//case Cell.CELL_TYPE_STRING: // 代表文本
				//String cellvalue = cell.getRichStringCellValue().getString();
				String str = cell.getStringCellValue();
				cellVal = str;
				break;
			case BLANK: // 空白格
				//case Cell.CELL_TYPE_BLANK: // 空白格
				String blankValue = cell.getStringCellValue();
				System.out.print(blankValue + "--（"+ cell.getErrorCellValue() +"）--" + "\t");
				cellVal = blankValue;
				break;
			case BOOLEAN: // 布尔型
				//case Cell.CELL_TYPE_BOOLEAN: // 布尔型
				boolean booleanCellValue = cell.getBooleanCellValue();
				cellVal = booleanCellValue;
				break;
			case NUMERIC: // 数字||日期
				//case Cell.CELL_TYPE_NUMERIC: // 数字||日期
				//判断是数字还是日期（org.apache.poi.ss.usermodel.DateUtil）
				boolean cellDateFormatted = DateUtil.isCellDateFormatted(cell);
				if (cellDateFormatted) {
					Date dateCellValue = cell.getDateCellValue();
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //cellVal = sdf.format(dateCellValue);
                    cellVal = dateCellValue;
					System.out.print("\t");
				} else {
                    double numericCellValue = cell.getNumericCellValue();
                    cellVal = judgeCellNumeric(numericCellValue);
					System.out.print("\t");
				}
				break;
			case ERROR: // 错误
				//case Cell.CELL_TYPE_ERROR: // 错误
				byte errorCellValue = cell.getErrorCellValue();
                cellVal = errorCellValue;
				break;
			case FORMULA: // 公式
				//case Cell.CELL_TYPE_FORMULA: // 公式
				String cachedFormulaResultType = cell.getCellFormula();
                cellVal = cachedFormulaResultType;
				break;
		}
        System.out.print(cellVal == null ? "NULL" : "-->"+ cellVal.toString());

		return cellVal;
	}

    private static Object judgeCellNumeric(double numericCellValue) {
	    String numericStr = String.valueOf(numericCellValue);

        System.out.println("judgeCellNumeric string："+ numericStr +"（length() = "+ numericStr.length()+"）");

        if(numericStr.indexOf("E") > -1){
            String double2Str = new DecimalFormat("#").format(numericCellValue);//("#.00")表示保留两位小数，这里不要小数

            if(double2Str.length() == 11 && StringUtil.isMobile(double2Str)){
                return double2Str;//也不是十分严谨，够用
            }else{
                return numericStr;//不处理,就返回字符串
            }
        }

        String endsWith0Regex = ".*\\.0+$";//匹配以 .000+结尾的，前面的.*不能少

        if(numericStr.matches(endsWith0Regex) || MathUtil.isNumber(numericStr)){
            //int val = Integer.parseInt(numericStr);//如果numericStr = 20.0，这种就会报错For input string: "20.0"
            int val = Double.valueOf(numericCellValue).intValue();
            return (Integer)val;
        }

        if(MathUtil.isDouble(numericStr)){
            Double val = Double.parseDouble(numericStr);
            return val;
        }

        System.out.println("judgeCellNumeric判断 Cell数值类型 的值 是魔鬼？返回字符串："+ numericStr);
	    return numericStr;
    }

}