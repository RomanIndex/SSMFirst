package com.ssm.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VisitPageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String any() {
        return "redirect:/system/route/login/login";
    }

    /* admin 和 index 都是默认定到 后台登录页面，如果有cookie，可以直接进入首页 */
    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String admin() {
        return "redirect:/admin/route/index";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "redirect:/admin/route/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "/system/login/login";
    }

    //备注下干嘛用的？
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String welcome() {
        return "admin/welcome";
    }

    /* 现在只放了 登录 有关的静态文件*/
    @RequestMapping(value = "system/route/{model}/{url}", method = RequestMethod.GET)
    public String system(@PathVariable String model, @PathVariable String url) {
        return "system/"+ model +"/"+ url;
    }

    //权限后台管理（设置菜单时，route不能漏）
    @RequestMapping(value = "admin/route/{url}", method = RequestMethod.GET)
    public String route(@PathVariable String url) {
        return "admin/"+ url;
    }

    //【预留】待开放的其他功能 页面统一跳转路由
    @RequestMapping(value = "ssm/route/{model}/{url}", method = RequestMethod.GET)
    public String qxyRoute(@PathVariable String model, @PathVariable String url) {
        return "ssm/"+ model +"/"+ url;
    }

    /**
     * 无权限操作 提示页面
     */
    @RequestMapping(value = "admin/noAuthority/page", method = RequestMethod.GET)
    public String noAuthorityPage() {
        return "/admin/examples/404.ftl";
    }

    /**
     * 利用ftl做页面跳转，并用model往页面-塞数据-的方式
     * @param model
     * @return
     */
    @RequestMapping(value = "mg/admin/privilege/add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("operateList", operateService.listOperate());
        model.addAttribute("firstMenu", moduleService.getFirstMenu());
        model.addAttribute("operate", "add");
        return "system/privilege_edit";
    }

    @RequestMapping(value = "mg/admin/privilege/update", method = RequestMethod.GET)
    public String update(Model model, String signId){
        SsmPrivilege privilege = privilegeService.getPrivilegeBySignId(signId);
        SsmModule module = moduleService.getModuleBySignId(privilege.getModuleId());
        int mType = 1;
        if(module.getType() == 2 && null == module.getParentId()){
            //二级菜单
            model.addAttribute("secondMenu", moduleService.getSecondMenuByParentId(module.getParentId()));
            mType = 2;
        }else if (module.getType() == 1) {
            //按钮
            SsmModule faModule = moduleService.getModuleBySignId(module.getBelongId());
            model.addAttribute("secondMenu", moduleService.getSecondMenuByParentId(faModule.getParentId()));
            model.addAttribute("btn", moduleService.getBtnByBelongId(module.getBelongId()));
            mType = 3;
        }
        model.addAttribute("firstMenu", moduleService.getFirstMenu());//:::需要根据moduleId查询所有父节点的SQL
        model.addAttribute("mType", mType);
        model.addAttribute("object", privilege);//初始化有问题，不对
        model.addAttribute("operateList", operateService.listOperate());
        model.addAttribute("operate", "update");
        return "system/privilege_edit";
    }
}
