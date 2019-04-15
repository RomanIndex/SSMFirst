package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleJpaDao extends JpaRepository<SsmModule, String> {
    List<SsmModule> findByTypeAndParentIdIsNull(int i);

    List<SsmModule> findByTypeAndBelongId(int i, String belongId);
}
