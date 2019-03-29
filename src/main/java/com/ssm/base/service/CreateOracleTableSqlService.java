package com.ssm.base.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssm.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssm.base.entity.ExcelTableField;

@Service 
public class CreateOracleTableSqlService {
	
	public Map<String, String> createSql(Map<String, List<ExcelTableField>> xxMap) {
		
		Map<String, String> sqlMap = new HashMap<>();//key=数据库表名，value=生成语句sql
		
		//hhee，半天，遍历map
		for(Map.Entry<String, List<ExcelTableField>> eachColumnMap : xxMap.entrySet()){
			String sheetName = eachColumnMap.getKey();//sheet页 要以实体类名 命名
			List<ExcelTableField> eachColumn = eachColumnMap.getValue();
			
			StringBuilder createStr = new StringBuilder();//1、建表
			
			//String tableName = clzz.getName();//全名eg：com.ssm.base.entity.AaTest
			//tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
			String tableName = StringUtil.hump2underline(sheetName);//驼峰转下划线（顺便转大写）
			System.out.println("（要驼峰转下划线）tableName："+ tableName);
			
			//createStr.append("\r\n DROP TABLE "TEXT_CREATE_SQL" CASCADE CONSTRAINTS;").append("\r\n");
			createStr.append("create table ").append(tableName).append("\n(\r\n");
			
			StringBuilder commentStr = new StringBuilder();//2、字段说明
			
			String tableComment = tableName + "（自动建表）";
			StringBuilder tableCommentStr = new StringBuilder("comment on table "+ tableName +" is \'"+ tableComment +"\';\n");//3、表名注释
			
			StringBuilder pkColumn = new StringBuilder();//主键字段
			
			for(ExcelTableField eachZD : eachColumn){
				createStr.append("  "+ eachZD.getColumn()).append(" ").append(eachZD.getType());
				
				if(StringUtils.isNotBlank(eachZD.getDefaultValue())){
					createStr.append(" default "+ eachZD.getDefaultValue());
				}
				
				if(!eachZD.isAbleNull()){
					//默认为true，即可以为空
					createStr.append(" not null");
				}
				
				if(eachZD.isPrimary()){
					pkColumn.append(eachZD.getColumn() +",");
				}
				
				createStr.append(",\n");
				
				if(StringUtils.isNotBlank(eachZD.getRemark())){
					commentStr.append("comment on column "+ tableName +"."+ eachZD.getColumn() +" is \'"+ eachZD.getRemark() +"\';\n");
				}else{
					commentStr.append("comment on column "+ tableName +"."+ eachZD.getColumn() +" is \'"+ eachZD.getName() +"\';\n");
				}
				
			}
			createStr.deleteCharAt(createStr.length() - 2);//**去掉最后一个字段后的 逗号（,），十分重要
			createStr.append("\n)\n;\n");
			
			String pkString = "";
			if(StringUtils.isNotBlank(pkColumn.toString())){
				//alter，，，不要拼错
				pkString = "alter table "+ tableName +"\n  add constraint "+ tableName +"_PK primary key ("+ pkColumn.deleteCharAt(pkColumn.length() - 1) +");\n";
			}
			
			String sql = createStr + "\n" + tableCommentStr + "\n" + commentStr + "\n" + pkString;
			System.out.println(sql);
			sqlMap.put(sheetName, sql);
			createSqlFile(sheetName, sql);
		}
		
		return sqlMap;
		
	}

	private void createSqlFile(String sheetName, String sql) {
		//生成sql语句文件
		try {
			String sqlPath = "D:\\LocalPicDev\\实体类"+ sheetName +"建表语句.sql";
			File file = new File(sqlPath);
			
			if (!file.exists()) {//文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());  
                dir.mkdirs();
                file.createNewFile(); 
            }
			
			byte[] bytes = sql.getBytes();
			OutputStream os = new FileOutputStream(sqlPath);
			os.write(bytes);
	        os.flush();
	        os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}