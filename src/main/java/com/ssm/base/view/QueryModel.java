package com.ssm.base.view;

public class QueryModel {
	/**
	 * 分页相关的三个参数
	 */
	private int pageNo;//第几页
	
	private int pageSize;//每页的记录数（即每页最多允许的条数）

	private int pageStart;//mysql分页要用到
	
	private int totalRecords;//返回的总记录数
	
	/**
	 * 接收时间的参数，这里有支持两种类型
	 */
	private String beginDate;//字符串类型，统一格式，方便处理，如："2017-12-01"
	
	private String endDate;

	public int getPageStart() {
		pageStart = (pageNo - 1) * pageSize;
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
