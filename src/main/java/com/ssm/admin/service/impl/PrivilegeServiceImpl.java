package com.ssm.admin.service.impl;

import com.ssm.admin.dao.PrivilegeMapper;
import com.ssm.admin.daoJpa.PrivilegeJpaDao;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.entity.SsmRolePrivilege;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.service.RolePrivilegeService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.admin.view.OperateView;
import com.ssm.admin.view.PrivilegeView;
import com.ssm.base.view.PageModel;
import com.ssm.base.view.Result;
import com.ssm.common.enumeration.OperateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivilegeServiceImpl extends CommonServiceImpl<SsmPrivilege, String> implements PrivilegeService {
    @Autowired private PrivilegeMapper privilegeMapper;
    @Autowired private RolePrivilegeService rolePrivilegeService;
    @Autowired private PrivilegeJpaDao privilegeJpaDao;

    @Override
    public Result<?> query(AdminQueryView query) {
        PageModel<PrivilegeView> pageModel = new PageModel<PrivilegeView>();
        pageModel.setTotalRecords(privilegeMapper.getCount(query));
        pageModel.setPageNo(query.getPageNo());
        pageModel.setPageSize(query.getPageSize());
        pageModel.setList(privilegeMapper.query(query));

        return Result.success(pageModel);
    }

    /* 操作类枚举 转 集合列表*/
    @Override
    public Result<?> getOperateList() {
        List<OperateView> list = new ArrayList<>();
        for(OperateEnum operateEnum : OperateEnum.values()){
            OperateView view = new OperateView();
            view.setOperate(operateEnum.name());
            view.setName(operateEnum.getName());
            list.add(view);
        }
        return Result.success(list);
    }

    @Override
    public Result<?> getPrivilegeByRole(String roleId) {
        List<SsmRolePrivilege> midList = rolePrivilegeService.getByRole();
        List<String> codes = midList.stream().map(i -> i.getPriCode()).collect(Collectors.toList());
        List<SsmPrivilege> privileges = this.selectAllById(codes);
        return Result.success(privileges);
    }

    @Override
    public List<SsmPrivilege> getTicket(OperateEnum type) {
        return privilegeJpaDao.findByOperateEnumName(type.name());
    }

    @Override
    public List<SsmPrivilege> getTicket(String roleId, OperateEnum type) {
        return privilegeJpaDao.findByOperateEnumNameAndRoleId(type.name(), roleId);
    }
}
