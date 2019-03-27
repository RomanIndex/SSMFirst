package com.ssm.base.Enum;

public enum OperateTypeEnum {
	update("更新"),
	add("新增");
	
	private String typeName;

	private OperateTypeEnum(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
