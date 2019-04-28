package com.ssm.admin.web;

import com.ssm.base.Enum.AuthorityTypeEnum;
import com.ssm.base.util.Authority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UniformPageController {

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
        return "system/login/login";
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
    @Authority(AuthorityTypeEnum.Validate)
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
    @RequestMapping(value = "admin/authority/reject", method = RequestMethod.GET)
    public String reject() {
        /**
         * org.springframework.web.util.NestedServletException:
         * Request processing failed;
         * nested exception is java.lang.IllegalArgumentException:
         * Non-normalized name, starts with "/": /admin/examples/404_zh_CN.ftl
         *
         * 还有：ftl页面的import路径检查对不对
         */
        //return "/admin/examples/404";
        //return "/admin/examples/404.ftl";//what? .ftl ?
        return "admin/examples/authority_reject";
    }

    @RequestMapping(value = "admin/authority/annotation", method = RequestMethod.GET)
    public String missAnnotation() {
        return "admin/examples/miss_annotation";
    }

    @RequestMapping(value = "admin/authority/code", method = RequestMethod.GET)
    public String missCode() {
        return "admin/examples/miss_code";
    }

    @RequestMapping(value = "admin/authority/module", method = RequestMethod.GET)
    public String missModule() {
        return "admin/examples/miss_module";
    }

}
