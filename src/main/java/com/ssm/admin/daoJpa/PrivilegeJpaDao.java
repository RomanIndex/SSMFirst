package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeJpaDao extends JpaRepository<SsmPrivilege, String> {
    List<SsmPrivilege> findByOperateEnumName(String name);

    List<SsmPrivilege> findByCodeIn(List<String> codes);

    SsmPrivilege findByModuleId(String moduleId);
}
