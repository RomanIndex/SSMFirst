package com.ssm.base.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.*;

import com.ssm.base.itface.Add;
import com.ssm.base.itface.SelfGenderCase;
import com.ssm.base.itface.Update;

@Table(name = "SC_USER")
//@GroupSequence({Account.class, Add.class, Update.class})
public class Account {
    @Id
	private String id;

    @NotBlank(message = "account不能为空！", groups = {Update.class})
    @Size(min = 2, max = 5, message = "account要在2-5个字符之间", groups = {Update.class})
    private String account;

    private String password;

    @NotBlank(message = "role也不能为空！！", groups = {Add.class, Update.class})
    private String role;
    
    //-----------------------------------
    private String name;
    
    private String mobile;
    
    @NotNull(message = "email不能为空！")
    private String email;
    
    @Min(0)
    private Integer age;
    
    private String birth;
    
    private String userType;

    //自定义注解，接口 和 实现类 都在itface包下
    @SelfGenderCase
    private String avatar;
    
    private Date createTime;
    
    private Integer status;
    
    private String remark;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	//注意：此处 @在 getter方法上，看test里面的测试说明：validateProperty不光是对field的值进行校验，还会对getter方法也进行校验
	@Max(60)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}