package com.ssm.admin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleMapper {
    int deleteByRoleIds(@Param("empNo") String empNo, @Param("delList") List<String> delList);
}
