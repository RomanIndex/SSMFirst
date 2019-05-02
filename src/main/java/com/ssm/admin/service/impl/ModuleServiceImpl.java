package com.ssm.admin.service.impl;

import com.ssm.admin.dao.ModuleMapper;
import com.ssm.admin.daoJpa.ModuleJpaDao;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.view.RecursionChildVo;
import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleServiceImpl extends CommonServiceImpl<SsmModule, String> implements ModuleService {
    @Autowired private ModuleJpaDao moduleJpaDao;
    @Autowired private ModuleMapper moduleMapper;

    @Override
    /* 利用ftl页面，打算用于管理module的另一种方式*/
    public Result<?> listByRole(String roleId) {
        List<SsmModule> modules = moduleJpaDao.findAll();
        return Result.success(this.module2ChildVo(modules));
    }

    @Override
    /* 左侧菜单，并且是生成票据的，只有在过滤器才用到 */
    public Result<?> privilege2menu(List<SsmPrivilege> privileges) {
        List<String> moduleIds = privileges.stream().map(i -> i.getModuleId()).collect(Collectors.toList());
        List<SsmModule> modules = moduleJpaDao.findByModuleIdIn(moduleIds);
        return Result.success(this.module2ChildVo(modules));
    }

    @Override
    public SsmModule getByUrl(String url) {
        return moduleJpaDao.findByUrlAndStatus(url, true);
    }

    /* type = 1 的作为预留，最高模块，现在后台并没有用到*/
    @Override
    public Result<?> getTopMenu() {
        /* mysql 巨坑之 【null 和 空值】*/
        List<SsmModule> jpaList = moduleJpaDao.findByTypeAndParentId(2, "");
        return Result.success(jpaList);
    }

    @Override
    public Result<?> getSecondMenu(String parentId) {
        List<SsmModule> list = moduleJpaDao.findByTypeAndParentId(2, parentId);
        return Result.success(list);
    }

    //1：模块；2：菜单；3：按钮（路由也算其中一种）
    @Override
    public Result<?> getBtnMenu(String belongModule) {
        List<SsmModule> list = moduleJpaDao.findByTypeAndBelongModule(3, belongModule);
        return Result.success(list);
    }

    @Override
    /* 封装成treegrid可以解析的数据格式，没有经过递归处理的，可以 和 菜单的 整合一起 */
    public List<TreegridView> getTreegridView(String moduleName) {
        //List<TreegridView> treegridViews = moduleMapper.getMenuTreegrid(moduleName);
        List<TreegridView> treegridViews = this.module2treegrid();
        return treegridViews;
    }

    private List<TreegridView> module2treegrid() {
        List<SsmModule> allModule = this.selectAll();
        List<TreegridView> list = new ArrayList<>();
        for(SsmModule base : allModule){
            TreegridView view = new TreegridView();
            view.setModuleId(base.getModuleId());
            view.setType(base.getType());
            view.setName(base.getName());
            view.setUrl(base.getUrl());
            view.setSeq(base.getSeq());
            //这里不要封装SsmTicket，因为还不知道module有没有生成票据
            //确定parentId，又一次Mysql null 和 空 引发的BUG现场
            String parentId = null;
            if(StringUtils.isNotBlank(base.getBelongModule())){
                parentId = base.getBelongModule();
            }
            if(StringUtils.isNotBlank(base.getParentId())){
                parentId = base.getParentId();
            }
            view.set_parentId(parentId);
            list.add(view);
        }
        return list;
    }

    /**
     * -------------------将 module 转换成 childVo，且递归处理好（左侧菜单、模块ftl管理页面）-------------------------------
     */
    private List<RecursionChildVo> module2ChildVo(List<SsmModule> modules){
        //module转成常用childVo
        List<RecursionChildVo> childVoList = this.setModule2ChildVo(modules);
        //递归处理childVo
        List<RecursionChildVo> recursionChildVos = this.recursionHandle(childVoList);

        return recursionChildVos;
    }

    private List<RecursionChildVo> recursionHandle(List<RecursionChildVo> primitiveList) {
        List<RecursionChildVo> topList = new ArrayList<RecursionChildVo>();

        // 先找到所有顶级节点，也就是parentId为空的那些
        for (int i = 0; i < primitiveList.size(); i++) {
            // 顶级节点没有parentId
            if (StringUtils.isBlank(primitiveList.get(i).getParentId())) {
                topList.add(primitiveList.get(i));
            }
        }

        // 为顶级节点设置子节点，getChild是递归调用的
        for (RecursionChildVo each : topList) {
            each.setChildren(getChild(each.getId(), primitiveList));
        }

        return topList;
    }

    /**
     * 递归查找子节点（用你学的数据结构与算法优化一下?）
     *
     * @param id 当前节点id
     * @param primitiveList 要查找的列表
     * @return
     */
    private List<RecursionChildVo> getChild(String id, List<RecursionChildVo> primitiveList) {
        List<RecursionChildVo> childList = new ArrayList<>();

        for (RecursionChildVo each : primitiveList) {
            // 遍历所有节点，将父节点id与传过来的id比较
            if (id.equals(each.getParentId())) {
                childList.add(each);
            }
        }

        // 把子节点的子节点再循环一遍
        for (RecursionChildVo child : childList) {
            child.setChildren(getChild(child.getId(), primitiveList));
        }

        // 递归退出条件
        if (childList.size() <= 0) {
            return null;
        }

        return childList;
    }

    private List<RecursionChildVo> setModule2ChildVo(List<SsmModule> modules) {
        List<RecursionChildVo> menus = modules.stream().map(each -> {
            RecursionChildVo menu = new RecursionChildVo();
            /* belongModule 和 parentId 应该是 “互斥” 的，即同时最多只能一个有值，且谨防null和空 */
            String parentId = StringUtils.isBlank(each.getParentId()) ? each.getBelongModule() : each.getParentId();
            menu.setParentId(parentId);
            menu.setId(each.getModuleId());
            menu.setName(each.getName());
            menu.setUrl(each.getUrl());
            menu.setSeq(each.getSeq());
            menu.setIcon(each.getStyle());
            menu.setCreateTime(each.getCreateTime());
            menu.setStatus(each.isStatus());
            return menu;
        }).collect(Collectors.toList());
        return menus;
    }

}
