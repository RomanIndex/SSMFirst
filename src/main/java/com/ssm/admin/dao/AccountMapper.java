package com.ssm.admin.dao;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.view.AccountView;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.common.multiDataSource.wwOracleMapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AccountMapper {
    int getCount(AdminQueryView query);

    List<AccountView> query(AdminQueryView query);
}
