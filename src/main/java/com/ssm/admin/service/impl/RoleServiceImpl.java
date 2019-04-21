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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends CommonServiceImpl<SsmRole, String> implements RoleService {
    @Autowired private RoleJpaDao roleJpaDao;
    @Autowired private AccountRoleService accountRoleService;

    @Override
    public Result<?> jpaQuery(AdminQueryView param) {
        //查询条件构造
        Specification<SsmRole> spec = new Specification<SsmRole>() {
            @Override
            // Root用于获取属性字段，CriteriaQuery可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<SsmRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //集合 用于封装查询条件
                List<Predicate> list = new ArrayList<Predicate>();
                //like用法
                if(StringUtils.isNotBlank(param.getRoleKeyword())){
                    Predicate p2 = cb.like(root.get("name").as(String.class), "%"+ param.getRoleKeyword() +"%");
                    //Predicate p2 = cb.like(root.<String>get("name"), "%" + param.getRoleKeyword() + "%");
                    list.add(p2);
                }
                //between用法
                if ((null != param.getBeginDate()) && (null != param.getEndDate())) {
                    //要转成Date类型
                    Predicate pDate = cb.between(root.get("createDate"), param.getBeginDate(), param.getEndDate());
                    list.add(pDate);
                }
                //多表查询 https://blog.csdn.net/a772304419/article/details/79052837
                /*Join<SsmRole, SsmRolePrivilege> privilegeJoin = root.join("roleId", JoinType.LEFT);
                if(param.getPrivilegeKeyword() != null){
                    Predicate pJoin = cb.equal(privilegeJoin.get("roleId").as(String.class), param.getPrivilegeKeyword());
                    list.add(pJoin);
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
