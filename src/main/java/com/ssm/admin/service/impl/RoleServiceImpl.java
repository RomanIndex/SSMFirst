package com.ssm.admin.service.impl;

import com.ssm.admin.daoJpa.AccountRoleJpaDao;
import com.ssm.admin.daoJpa.RoleJpaDao;
import com.ssm.admin.entity.SsmAccountRole;
import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.AccountRoleService;
import com.ssm.admin.service.RoleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends CommonServiceImpl<SsmRole, String> implements RoleService {
    @Autowired private AccountRoleService accountRoleService;
    @Autowired private RoleJpaDao roleJpaDao;

    //@Override
    public Result<?> findByPageAndParams(AdminQueryView query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");//2.x写法，推荐
        Pageable pageable = PageRequest.of(query.getPageNo() - 1, query.getPageSize(), sort);
        Page<SsmRole> pageData = this.page(pageable);//js取值也要对应的改
        return Result.success(pageData);
    }

    @Override
    public Result<?> jpaQuery(AdminQueryView param) {
        Specification<SsmRole> spec = new Specification<SsmRole>() { //查询条件构造
            @Override
            // Root 用于获取属性字段，CriteriaQuery可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<SsmRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //集合 用于封装查询条件
                List<Predicate> list = new ArrayList<Predicate>();
                //简单单表查询
                /*if(StringUtils.isNotBlank(param.getAccountKeyword())){
                    Predicate p1 = cb.equal(root.get("accountKeyword").as(String.class), param.getAccountKeyword());
                    list.add(p1);
                }*/

                if(StringUtils.isNotBlank(param.getRoleKeyword())){
                    Predicate p2 = cb.like(root.get("name").as(String.class), "%"+ param.getRoleKeyword() +"%");
                    list.add(p2);
                }

                //多表查询 https://blog.csdn.net/a772304419/article/details/79052837
                /*Join<AdminQueryView, AdminQueryView> standardJoin = root.join("standard",JoinType.INNER);
                if(param.getRoleKeyword().getName()!=null){
                    Predicate p4 = cb.like(standardJoin.get("name").as(String.class),"%"+courier.getStandard().getName()+"%" );
                    list.add(p4);
                }*/

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");//2.x写法，推荐
        Pageable pageable = PageRequest.of(param.getPageNo() - 1, param.getPageSize(), sort);//分页信息
        Page<SsmRole> pageData = roleJpaDao.findAll(spec, pageable);

        //将返回page对象转换为datagrid所需要的数据格式
        /*Map<String,Object> map = new HashMap<>();
        map.put("total", pageData.getTotalElements());
        map.put("rows", pageData.getContent());*/

        return Result.success("JPA带条件分页查询success", pageData);
    }

    @Override
    public Result<?> getRoleByAccount(String empNo) {
        List<SsmAccountRole> midList = accountRoleService.getByEmpNo(empNo);
        List<String> roleIds = midList.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
        List<SsmRole> roles = this.selectAllById(roleIds);
        return Result.success(roles);
    }
}
