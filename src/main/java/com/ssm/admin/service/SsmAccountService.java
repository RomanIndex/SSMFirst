package com.ssm.admin.service;

import java.util.List;

import javax.validation.groups.Default;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.admin.dao.ISsmAccountDao;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.base.Enum.OperateTypeEnum;
import com.ssm.base.itface.Add;
import com.ssm.base.util.ValidationUtil;
import com.ssm.base.view.PageModel;
import com.ssm.base.view.QueryModelView;
import com.ssm.base.view.Result;

import tk.mybatis.mapper.entity.Example;

@Service
public class SsmAccountService {
	@Autowired private ISsmAccountDao accountDao;

	public Result<?> mapperQuery(QueryModelView query) {
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
	}

	public Result<?> getAccountByKey(String empNo) {
		return new Result<>(0, "", "", accountDao.selectByPrimaryKey(empNo));
	}

	public Result<?> mapperSave(SsmAccount account, String operate) {
		Result<?> result = new Result<>();
		//方式一、不通过这个抛出异常，并直接 return 结果
		/*try {
			//List<String> validList = ValidationUtil.validate(account, Default.class, Add.class);
			ValidationUtil.validate(account);
		} catch(ValidationException e){
		    e.printStackTrace();
		    result.setMsg(e.getMessage());//返回校验结果给前台
		    return result;
		}*/
		
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
		if(operate.equals(OperateTypeEnum.update.toString())){
			if(accountDao.updateByPrimaryKeySelective(account) > 0){
				result.setCode(0);
				result.setMsg("修改成功！");
			}
		}else if (operate.equals(OperateTypeEnum.add.toString())) {
			account.setPassword("4QrcOUm6Wau+VuBX8g+IPg==");//123456
			if(accountDao.insertSelective(account) > 0){
				result.setCode(0);
				result.setMsg("新增成功！");
			}
		}else {
			result.setMsg("不存在的操作类型！");
		}
		
		return result;
	}

}