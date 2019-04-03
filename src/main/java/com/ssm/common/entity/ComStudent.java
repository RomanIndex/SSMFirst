package com.ssm.common.entity;

import java.util.Date;

public class ComStudent {
    private String name;

    private Integer age;

    private double money;//1.78RMB//之前用Double，反射一直报cast异常，即不能把Integer转Double，改成double就好了

    private String mobile;

    private Date createTime;

    private int status;

    //特殊，测试反射用到，私有构造函数
    private ComStudent(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public ComStudent() {
    }

    public ComStudent(String name, Integer age, double money, String mobile, Date createTime, int status) {
        this.name = name;
        this.age = age;
        this.money = money;
        this.mobile = mobile;
        this.createTime = createTime;
        this.status = status;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
