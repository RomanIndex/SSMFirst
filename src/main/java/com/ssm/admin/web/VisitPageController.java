package com.ssm.admin.web;

import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
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
        model.addAttribute("operateList", privilegeService.getOperateList());
        model.addAttribute("firstMenu", moduleService.getTopMenu());
        model.addAttribute("operate", "add");
        return "admin/privilege_edit";
    }

    @RequestMapping(value = "admin/privilege/update", method = RequestMethod.GET)
    public String update(Model model, String code){
        SsmPrivilege privilege = privilegeService.getById(code);
        SsmModule module = moduleService.getById(privilege.getModuleId());
        int mType = 1;
        if(module.getType() == 2 && null == module.getParentId()){
            //二级菜单
            model.addAttribute("secondMenu", moduleService.getSecondMenu(module.getBelongModule()));
            mType = 2;
        }else if (module.getType() == 1) {
            //按钮
            SsmModule faModule = moduleService.getById(module.getBelongModule());
            model.addAttribute("secondMenu", moduleService.getSecondMenu(faModule.getBelongModule()));
            model.addAttribute("btn", moduleService.getBtnMenu(module.getBelongModule()));
            mType = 3;
        }
        model.addAttribute("firstMenu", moduleService.getTopMenu());//:::需要根据moduleId查询所有父节点的SQL
        model.addAttribute("mType", mType);
        model.addAttribute("object", privilege);//初始化有问题，不对
        model.addAttribute("operateList", privilegeService.getOperateList());
        model.addAttribute("operate", "update");
        return "admin/privilege_edit";
    }
}
