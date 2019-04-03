package com.ssm.common.service;

import com.ssm.common.entity.ComStudent;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
/**
 * ---------------仅供测试用--------------
 */
public class TestStudentService {

    /**
     * 获取单个实例对象
     * 属性值 应该 要随机生成
     */
    public ComStudent createSingleObject(){
        ComStudent student = new ComStudent();
        student.setName("月崖");
        student.setAge(18);
        student.setMobile("13699794520");
        student.setCreateTime(Calendar.getInstance().getTime());
        student.setMoney(250.68);
        student.setStatus(1);
        return student;
    }
}
