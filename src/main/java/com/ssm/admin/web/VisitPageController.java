package com.ssm.admin.web;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VisitPageController {
    @Autowired private PrivilegeService privilegeService;
    @Autowired private ModuleService moduleService;

    /**
     * 利用ftl做页面跳转，并用model往页面-塞数据-的方式
     * @param model
     * @return
     */
    @RequestMapping(value = "admin/privilege/add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("operateList", privilegeService.getOperateList().getData());
        model.addAttribute("topMenu", moduleService.getTopMenu().getData());
        model.addAttribute("operate", "add");
        return "admin/privilege_edit";
    }

    @RequestMapping(value = "admin/privilege/update", method = RequestMethod.GET)
    public String update(Model model, String code){
        SsmPrivilege privilege = privilegeService.getById(code);
        SsmModule module = moduleService.getById(privilege.getModuleId());
        int upType = module.getType();
        if(module.getType() == 2 && StringUtils.isNotBlank(module.getParentId())){
            //更新 二级菜单
            model.addAttribute("secondMenu", moduleService.getSecondMenu(module.getParentId()).getData());
        }else if (module.getType() == 3) {
            //更新 按钮
            SsmModule secondMenu = moduleService.getById(module.getBelongModule());
            model.addAttribute("secondMenu", moduleService.getSecondMenu(secondMenu.getParentId()));
            model.addAttribute("btnMenu", moduleService.getBtnMenu(module.getBelongModule()));
        }
        model.addAttribute("topMenu", moduleService.getTopMenu().getData());//始终都需要返回
        model.addAttribute("upType", upType);//页面不需要，仅供验证用
        model.addAttribute("object", privilege);//---初始化有问题，不对
        model.addAttribute("operateList", privilegeService.getOperateList().getData());
        model.addAttribute("operate", "update");
        return "admin/privilege_edit";
    }
}