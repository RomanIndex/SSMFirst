package com.ssm.admin.daoJpa;

import com.ssm.common.multiDataSource.wwOracleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssm.admin.entity.SsmAccount;

import tk.mybatis.mapper.common.Mapper;
public interface AccountJpaDao extends JpaRepository<SsmAccount, String> {

    SsmAccount findByEmpNoAndStatus(String empNo, boolean status);
}
