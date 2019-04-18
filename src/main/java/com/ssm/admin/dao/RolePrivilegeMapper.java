package com.ssm.admin.dao;

import com.ssm.admin.view.RolePrivilegeView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePrivilegeMapper {
    void deleteByCodes(@Param("roleId") String roleId, @Param("delList")List<String> delList);

    List<RolePrivilegeView> getByRoleAndOperate(@Param("roleId") String roleId, @Param("operate") String operate);
}
