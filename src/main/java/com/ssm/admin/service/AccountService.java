package com.ssm.admin.service;

import com.ssm.admin.entity.SsmAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.admin.daoJpa.AccountJpaDao;

@Service
public class AccountService {
	@Autowired private AccountJpaDao accountJpaDao;

	/* -----------------------基于JPA的一套实现逻辑 -----------------------------------*/

	public SsmAccount findBy(String empNo) {
		SsmAccount account = accountJpaDao.findByEmpNoAndStatus(empNo, true);
		return account;
	}

	public SsmAccount getAccountById(String empNo) {
		SsmAccount account = accountJpaDao.findOne(empNo);
		return account;
	}

	public SsmAccount save(SsmAccount obj) {
		SsmAccount account = accountJpaDao.save(obj);
		return account;
	}

	public SsmAccount update(String empNo) {
		SsmAccount account = accountJpaDao.saveAndFlush(null);
		return account;
	}

	public SsmAccount delete(String empNo) {
		SsmAccount account = accountJpaDao.findOne(empNo);
		return account;
	}

	/* -------------------------Mapper 的一套实现逻辑【可谓标准化、极速开发】------------------------------------ */


	/*public Result<?> mapperQuery(QueryModelView query) {
		Example example = new Example(SsmAccount.class);
		Example.Criteria criteria = example.createCriteria();
		//criteria.andEqualTo("status", 1);//
		if(StringUtils.isNotBlank(query.getKeyWord())){
			criteria.andCondition("(name like '%"+ query.getKeyWord() +"%' or mobile like '%"+ query.getKeyWord() +"%')");
		}
		PageModel<SsmAccount> pageModel = new PageModel<>();
		pageModel.setTotalRecords(accountDao.selectCountByExample(example));
		pageModel.setPageNo(query.getPageNo());
		pageModel.setPageSize(query.getPageSize());
		RowBounds rowBound = new RowBounds((query.getPageNo() - 1) * query.getPageSize(), query.getPageSize());
		pageModel.setList(accountDao.selectByExampleAndRowBounds(example, rowBound));
		
		return new Result<>(0, "", "", pageModel);
	}*/

	/*public Result<?> listAccountByKey() {
		return new Result<>(0, "", "", accountDao.selectAll());
	}

	public Result<?> getAccountByKey(String empNo) {
		return new Result<>(0, "", "", accountDao.selectByPrimaryKey(empNo));
	}*/

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