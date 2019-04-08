package com.ssm.base.view;

import com.ssm.admin.entity.SsmBaseEntity;

/**
 * 传给前端数据的统一格式
 * code = 0时，表示调用成功，msg = OK！
 * 其余code均表示调用接口异常，异常时，标明异常码，并给出msg和detail注释，同步文档
 */
public class Result<T> {
	
	private int code;//状态返回码
	
	private String msg;//返回码描述
	
	private String detail;//错误详细描述或返回码对应处理方案
	
	private T data;//返回的主体数据
	
	public static final int FAIL = -1;//*修饰符public static
	
	public static final int SUCCESS = 0;
	
	public Result() {
		code = FAIL;
		msg = "Default Msg!";
	}
	
	public Result(int code, String msg, String detail, T data) {
		this.code = code;
		this.msg = msg;
		this.detail = detail;
		this.data = data;
	}

	public static <T extends SsmBaseEntity> Result<T> fail(String msg) {
		return new Result<>(FAIL, msg, null, null);
	}

	public static <T extends SsmBaseEntity> Result<T> success(String msg, T entity) {
		msg = msg == null ? "操作成功！" : msg;
		return new Result<>(SUCCESS, msg, null, null);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
