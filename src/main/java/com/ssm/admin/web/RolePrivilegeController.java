package com.ssm.admin.web;

import com.ssm.admin.service.RolePrivilegeService;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(description="角色权限API", value = "角色权限API", tags = {"SSM后台：角色权限接口"})
@CrossOrigin("*")
@RestController
@RequestMapping("ssm/admin/rolePrivilege")
public class RolePrivilegeController {
    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    //供 树形网络 使用（后续要做出可查询的）
    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    public Result<?> menuTreegrid(String roleId) {
        List<TreegridView> list = rolePrivilegeService.getMenuTreegrid(roleId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("rows", list);

        return Result.success("查询Treegrid成功！", map);
    }

    @ApiOperation(value = "（混合）更新 角色 的 权限", notes = "需要改进，只能 单边 的新增 或 删除")
    @RequestMapping(value = "/updateByRole", method = RequestMethod.POST)
    public Result<?> updateByRole(String roleId, String[] codes) {
        List<String> list = codes == null ? new ArrayList<>() : Arrays.asList(codes);
        return rolePrivilegeService.mixUpdatePrivilege(roleId, list);
    }
}
