package com.ssm.admin.web;

import com.ssm.admin.service.ModuleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description="模块API", value = "模块API", tags = {"SSM后台：模块接口（含左侧菜单）"})
@CrossOrigin("*")
@RestController
@RequestMapping("ssm/admin/module")
public class ModuleController {
    @Autowired private ModuleService moduleService;
}
