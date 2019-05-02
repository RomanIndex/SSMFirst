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
    public Result<?> checkAuth(String account, String authUrl) {
        SsmModule module = moduleService.getByUrl(authUrl);
        if(null == module){
            return new Result<>(502, "【" + authUrl + "】对应的模块为空！", null, null);
        }

        SsmPrivilege privilege = this.getByModule(module.getModuleId());
        boolean adminFreeUrl = this.adminFreeUrl(account, authUrl);
        if(adminFreeUrl){
            //初始化系统时，为admin初始化设置系统，需要额外放行的几个url
        }else{
            if(null == privilege){
                return new Result<>(503, "【" + authUrl + "】模块尚未注册成票据！", null, null);
            }
        }

        if(Config.SSM_MASTER_ACCOUNT.equalsIgnoreCase(account)){
            return Result.success("至高无上的admin光临系统！", "admin默认拥有所有存在的票据！");
        }

        List<SsmPrivilege> privileges = this.listByAccount(account);
        List<String> codes = privileges.stream().map(i -> i.getCode()).collect(Collectors.toList());
        if(codes.contains(privilege.getCode())){
            return Result.success("检测到用户已授权！", privilege);
        }

        return Result.fail("未检测到授权！");
    }

    private boolean adminFreeUrl(String account, String url) {
        List<String> freeUrl = new ArrayList<>();
        freeUrl.add("/admin/route/privilege/add");
        freeUrl.add("/admin/module/second");
        freeUrl.add("/admin/module/btn");
        freeUrl.add("/admin/privilege/add");
        if(freeUrl.contains(url) && account.equalsIgnoreCase("admin")){
            return true;
        }
        return false;
    }

    /* 以下是该类，也是数据表对应的通用查询，返回值也不是Result */

    @Override
    public SsmPrivilege getByModule(String moduleId) {
        return privilegeJpaDao.findByModuleId(moduleId);
    }

    @Override
    public List<SsmPrivilege> listByRole(String roleId) {
        List<SsmRolePrivilege> midList = rolePrivilegeService.getByRole(roleId);
        List<String> codes = midList.stream().map(i -> i.getPriCode()).collect(Collectors.toList());
        List<SsmPrivilege> privileges = this.selectAllById(codes);
        return privileges;
    }

    @Override
    public List<SsmPrivilege> listByRoleAndOperate(String roleId, OperateEnum operateEnum) {
        List<SsmPrivilege> privileges = this.listByRole(roleId);
        return privileges.stream().filter(f -> f.getOperateEnumName().equals(operateEnum.name())).collect(Collectors.toList());
    }

    @Override
    public List<SsmPrivilege> listByOperate(OperateEnum operateEnum) {
        return privilegeJpaDao.findByOperateEnumName(operateEnum.name());
    }

    @Override
    public List<SsmPrivilege> listByAccount(String empNo) {
        //admin是默认拥有系统全部权限的
        if(Config.SSM_MASTER_ACCOUNT.equalsIgnoreCase(empNo)){
            return this.selectAll();
        }
        List<SsmAccountRole> accountRoles = accountRoleService.getByEmpNo(empNo);
        //account拥有的角色
        List<String> roleIds = accountRoles.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
        List<SsmRolePrivilege> rolePrivileges = rolePrivilegeService.listDistinctByRoleIds(roleIds);
        //account所有 角色 的不重复的 权限
        List<String> codes = rolePrivileges.stream().map(i -> i.getPriCode()).collect(Collectors.toList());
        return this.selectAllById(codes);
    }

    @Override
    public List<SsmPrivilege> listByAccountAndOperate(String empNo, OperateEnum operateEnum) {
        List<SsmPrivilege> privileges = this.listByAccount(empNo);
        return privileges.stream().filter(f -> f.getOperateEnumName().equals(operateEnum.name())).collect(Collectors.toList());
    }
}
