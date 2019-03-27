package com.ssm.base.entity;

public class ExcelTableField {
	
	//中文字段
	private String name;
	
	//数据库 列名
	private String column;
	
	//实体类 属性值，严格遵守 驼峰命名
	private String property;
	
	//数据库字段 类型
	private String type = "VARCHAR2(32)";
	
	//可为空，即默认为true
	private boolean ableNull = true;
	
	//默认值
	private String defaultValue;
	
	//注释
	private String remark;
	
	//主键，默认false
	private boolean primary = false;
	
	//索引，默认false
	private boolean index = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAbleNull() {
		return ableNull;
	}

	public void setAbleNull(boolean ableNull) {
		this.ableNull = ableNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

}
