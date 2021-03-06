package com.ssm.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.dao.AccountMapper;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.AccountService;
import com.ssm.admin.view.AccountView;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.PageModel;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssm.admin.daoJpa.AccountJpaDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 4、实现 每个实体类 的接口，继承公共泛型的实现类（实现JPA的单表操作），同时实现 自己定义的接口，实现该实体类相关的业务功能
 */
@Service
//@Transactional
public class AccountServiceImpl extends CommonServiceImpl<SsmAccount, String> implements AccountService {
	@Autowired private AccountJpaDao accountJpaDao;
	@Autowired private AccountMapper accountMapper;

	/* -----------------------基于JPA的一套实现逻辑 -----------------------------------*/

	/* ---View用于mabatis，map可继承；Vo是给 调用接口返回的对象，要极具扩展性-- */

	@Override
	public Result<?> getVoById(String empNo) {

		/* 公共JPA的查询 */
		SsmAccount jpaAccount = this.getById(empNo);
		System.out.println("公共JPA查询："+ JSON.toJSONString(jpaAccount));

		/* ---------------------统统有问题，查不出来数据？？？-------------------- */

		SsmAccount obj = new SsmAccount();
		obj.setEmpNo("YH6666");
		SsmAccount example = this.getByExample(obj);
		System.out.println("Example JPA查询："+ JSON.toJSONString(example));

		/* 自定义JPA的查询 */
		SsmAccount customAccount = accountJpaDao.findByEmpNo(empNo);
		System.out.println("自定义JPA查询："+ JSON.toJSONString(customAccount));

        List<SsmAccount> list = accountJpaDao.findAll();
        System.out.println("自定义JPA查询getOne："+ JSON.toJSONString(list));

		/* mybatis mapper的查询 */
		SsmAccount record = new SsmAccount();
		record.setEmpNo(empNo);
		/*SsmAccount mapperAccount = accountMapper.selectOne(record);
		System.out.println("Mapper查询："+ JSON.toJSONString(mapperAccount));*/

		return Result.success(null);
	}

	@Override
	public Result<?> jpaQuery(AdminQueryView query) {
		//Sort sort = new Sort(Sort.Direction direction, List<String> properties)//多属性的Sort实例
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");//2.x写法，推荐

		Pageable pageable = PageRequest.of(query.getPageNo() - 1, query.getPageSize(), sort);//2.x写法，推荐
		//Pageable pageableNest = pageable.next();

		Page<SsmAccount> pageData = this.page(pageable);//js取值也要对应的改

		return Result.success(pageData);
	}

	/**
	 * 自己定义的， 连表、条件过滤、排序、分页等 查询，用mybatis
	 * @param query
	 * @return
	 */
	@Override
	public Result<?> query(AdminQueryView query) {
		PageModel<AccountView> pageModel = new PageModel<AccountView>();
		pageModel.setTotalRecords(accountMapper.getCount(query));
		pageModel.setPageNo(query.getPageNo());
		pageModel.setPageSize(query.getPageSize());
		pageModel.setList(accountMapper.query(query));

		return Result.success(pageModel);
	}

	/* -------------------------Mapper 的一套实现逻辑【可谓标准化、极速开发】------------------------------------ */

	/*public Result<?> mapperSave(SsmAccount account, String operate) {
		Result<?> result = new Result<>();
		//方式一、不通过这个抛出异常，并直接 return 结果
		*//*try {
			//List<String> validList = ValidationUtil.validate(account, Default.class, Add.class);
			ValidationUtil.validate(account);
		} catch(ValidationException e){
		    e.printStackTrace();
		    result.setMsg(e.getMessage());//返回校验结果给前台
		    return result;
		}*//*
		
		//方式二
		//ValidationUtil.validate(account, Add.class);//不加Default.class，则 只会 校样加了Add.class的
		//List<String> validList = ValidationUtil.validate(account, Default.class, Add.class);
		List<String> validList = ValidationUtil.validate(account, Default.class, Add.class);
		if(validList.size() > 0){
			validList.forEach(s -> System.out.println(s));
			result.setMsg(validList.toString());//返回校验结果给前台
		    return result;
		}
		
		//String empNo = CookieHelper.getValueByName(Config.MEMBER_ID);
		if(operate.equals(OperateEnum.update.toString())){
			if(accountDao.updateByPrimaryKeySelective(account) > 0){
				result.setCode(0);
				result.setMsg("修改成功！");
			}
		}else if (operate.equals(OperateEnum.add.toString())) {
			account.setPassword("4QrcOUm6Wau+VuBX8g+IPg==");//123456
			if(accountDao.insertSelective(account) > 0){
				result.setCode(0);
				result.setMsg("新增成功！");
			}
		}else {
			result.setMsg("不存在的操作类型！");
		}
		
		return result;
	}*/

}