package com.ssm.admin.entity.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

//@Data
@Entity
@Table(name = "JPA_ACCOUNT")
public class JpaAccount {
    @Id
    //@GeneratedValue//异常Table 'otctest.hibernate_sequence' doesn't exist
    //@GeneratedValue(strategy=GenerationType.AUTO)//同上
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "age")
    private int age;

    @Column(name = "name")
    private String name;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time")
    private Date CreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
}
