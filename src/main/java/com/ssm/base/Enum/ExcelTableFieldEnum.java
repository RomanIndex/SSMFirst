package com.ssm.base.Enum;

public enum ExcelTableFieldEnum {
	
	name("中文名"),
	column("名称"),
	property("属性"),
	type("类型"),
	ableNull("可为空"),
	defaultValue("默认"),
	remark("注释"),
	primary("是否主键"),
	index("是否索引");
	
	private String describeName;

	private ExcelTableFieldEnum(String describeName) {
		this.describeName = describeName;
	}

	public String getDescribeName() {
		return describeName;
	}

	public void setDescribeName(String describeName) {
		this.describeName = describeName;
	}
	

}
