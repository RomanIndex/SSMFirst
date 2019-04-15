package com.ssm.admin.dao;

import com.ssm.admin.view.TreegridView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleMapper {
    List<TreegridView> getModuleForTreegrid(String name);
}
