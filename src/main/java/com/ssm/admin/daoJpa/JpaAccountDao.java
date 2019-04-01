package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.jpa.JpaAccount;
import com.ssm.common.multiDataSource.wwMysqlMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountDao extends JpaRepository<JpaAccount, Integer>, wwMysqlMapper {

}
