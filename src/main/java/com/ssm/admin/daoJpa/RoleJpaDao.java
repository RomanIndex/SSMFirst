package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 注意JpaDao都是不用 @Repository 注解的
 */
public interface RoleJpaDao extends JpaRepository<SsmRole, String>, JpaSpecificationExecutor<SsmRole>{
}
