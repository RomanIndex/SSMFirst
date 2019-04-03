package com.ssm.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssm.common.entity.ValidateAccount;
import com.ssm.base.view.QueryModelView;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IAccountDao extends Mapper<ValidateAccount>{

	//两分页查询sql，总页数、当前页数据
	int getActCount(@Param("query")QueryModelView queryView);//测试 新增对象 不要用@Param，否则xml里面插入字段要.的形式

	List<ValidateAccount> listAccount(@Param("query")QueryModelView queryView);

}
