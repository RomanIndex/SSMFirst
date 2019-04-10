package com.ssm.admin.service;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.param.AccountVo;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;

/**
 * 3、定义 每个实体类 的接口，并 继承 公共泛型接口（就拥有了公共接口定义的方法），同时注入 具体类型（相当于 “实现”泛型）
 */
public interface AccountService extends CommonService<SsmAccount, String> {
    AccountVo getVoById(String empNo);

    Result<?> query(AdminQueryView query);

    Result<?> jpaQuery(AdminQueryView query);
}
