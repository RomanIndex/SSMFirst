package com.ssm.admin.web;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.util.Authority;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description="权限API", value = "权限API", tags = {"SSM后台：权限接口"})
@CrossOrigin("*")
@RestController
@Authority
@RequestMapping("admin/privilege")
public class PrivilegeController {
    @Autowired private PrivilegeService privilegeService;
    @Autowired private ModuleService moduleService;

    @RequestMapping(value = "/operateList", method = RequestMethod.GET)
    public Result<?> getOperateEnumList() {
        return privilegeService.getOperateList();
    }

    /* 根据 角色 查询 拥有的 权限 */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result<?> getByRole(String roleId) {
        return Result.success(privilegeService.listByRole(roleId));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<?> query(AdminQueryView query) {
        return privilegeService.query(query);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(SsmPrivilege obj) {
        if(StringUtils.isBlank(obj.getModuleId())){
            return Result.fail("票据的moduleId不能为空！");
        }
        SsmModule module = moduleService.getById(obj.getModuleId());
        if(StringUtils.isBlank(module.getUrl())){
            return Result.fail("没有url的模块，无法创建票据！");
        }
        SsmPrivilege exist = privilegeService.getByModule(obj.getModuleId());
        if(ObjectUtils.notEqual(exist, null)){
            return Result.fail("模块无需重复创建票据！");
        }
        return privilegeService.create(obj);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<?> update(SsmPrivilege obj) {
        return privilegeService.update(obj);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result<?> del(String code) {
        return privilegeService.deleteById(code);
    }
}
