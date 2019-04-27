package com.ssm.admin.service.impl;

import com.ssm.admin.dao.PrivilegeMapper;
import com.ssm.admin.daoJpa.PrivilegeJpaDao;
import com.ssm.admin.entity.*;
import com.ssm.admin.service.AccountRoleService;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.service.RolePrivilegeService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.admin.view.OperateView;
import com.ssm.admin.view.PrivilegeView;
import com.ssm.base.view.Config;
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
    @Autowired private PrivilegeJpaDao privilegeJpaDao;
    @Autowired private RolePrivilegeService rolePrivilegeService;
    @Autowired private AccountRoleService accountRoleService;
    @Autowired private ModuleService moduleService;

    @Override
    public Result<?> query(AdminQueryView query) {
        PageModel<PrivilegeView> pageModel = new PageModel<PrivilegeView>();
        pageModel.setTotalRecords(privilegeMapper.getCount(query));
        pageModel.setPageNo(query.getPageNo());
        pageModel.setPageSize(query.getPageSize());
        List<PrivilegeView> list = privilegeMapper.query(query);
        List<OperateView> enumList = (List<OperateView>) getOperateList().getData();
        List<PrivilegeView> addEnumList = list.stream().map(i -> {
            enumList.stream().forEach(e -> {
                if(e.getOperate().equals(i.getOperateEnumName())){
                    i.setOperateName(e.getName());
                    return;
                }
            });
            return i;
        }).collect(Collectors.toList());
        pageModel.setList(addEnumList);

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
        List<SsmRolePrivilege> midList = rolePrivilegeService.getByRole(roleId);
        List<String> codes = midList.stream().map(i -> i.getPriCode()).collect(Collectors.toList());
        List<SsmPrivilege> privileges = this.selectAllById(codes);
        return Result.success(privileges);
    }

    @Override
    public List<SsmPrivilege> getTicket(OperateEnum type) {
        return privilegeJpaDao.findByOperateEnumName(type.name());
    }

    @Override
    public SsmPrivilege getTicket(String moduleId) {
        return privilegeJpaDao.findByModuleId(moduleId);
    }

    @Override
    public List<SsmPrivilege> listPrivilegeByAccount(String account, OperateEnum operate) {
        List<String> codes = this.listCodeByEmpNo(account);
        //系统 拥有的 指定 操作类型的 权限（这里是 菜单的）
        List<SsmPrivilege> privileges = privilegeJpaDao.findByOperateEnumName(operate.name());
        //codes 和 privileges 的 交集，就是传入的account的 所有菜单权限
        List<SsmPrivilege> codesOfAccount = privileges.stream().filter(i -> codes.contains(i.getCode())).collect(Collectors.toList());
        return Config.SSM_MASTER_ACCOUNT.equalsIgnoreCase(account) ? privileges : codesOfAccount;
    }

    private List<String> listCodeByEmpNo(String empNo){
        List<SsmAccountRole> accountRoles = accountRoleService.getByEmpNo(empNo);
        //account拥有的角色
        List<String> roleIds = accountRoles.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
        List<SsmRolePrivilege> rolePrivileges = rolePrivilegeService.listDistinctByRoleIds(roleIds);
        //account所有 角色 的不重复的 权限
        List<String> codes = rolePrivileges.stream().map(i -> i.getPriCode()).collect(Collectors.toList());
        return codes;
    }

    @Override
    public Result<?> checkAuth(String account, String authUrl) {
        SsmModule module = moduleService.getByUrl(authUrl);
        if(null == module){
            return Result.fail("【" + authUrl + "】对应的模块为空！");
        }

        SsmPrivilege privilege = this.getTicket(module.getModuleId());
        if(null == privilege){
            return Result.fail("【" + authUrl + "】模块尚未注册成票据！");
        }

        if(Config.SSM_MASTER_ACCOUNT.equalsIgnoreCase(account)){
            return Result.success("至高无上的admin光临系统！", "admin默认拥有所有存在的票据！");
        }

        List<String> codes = this.listCodeByEmpNo(account);
        if(codes.contains(privilege.getCode())){
            return Result.success("检测到用户已授权！", privilege);
        }

        return Result.fail("未检测到授权！");
    }
}
