package com.ssm.admin.dao;

import com.ssm.admin.view.AdminQueryView;
import com.ssm.admin.view.PrivilegeView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeMapper {
    int getCount(AdminQueryView query);

    List<PrivilegeView> query(AdminQueryView query);
}
