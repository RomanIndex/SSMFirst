package com.ssm.admin.service.impl;

import com.ssm.admin.entity.SsmBaseEntity;
import com.ssm.admin.service.CommonService;
import com.ssm.base.service.ReflectFieldService;
import com.ssm.base.view.Result;
import com.ssm.common.util.BeanUtil;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 2、公共 泛型接口 的实现类，
 * @param <T>
 * @param <ID>
 */

//@Service（加这个下面JpaRepository就报错，why？）
public class CommonServiceImpl<T extends SsmBaseEntity, ID extends Serializable> implements CommonService<T, ID> {

    /**
     * 【实现关键】：：：在 公共实现类 引入JpaRepository
     * JpaRepository接口 也是 继承了 分页排序 和 Example接口的
     * 总之，JPA的单表操作都可以在公共类，即这个类里面实现
     * 且对外暴露的方法名，可以自己定义
     */
    @Autowired private JpaRepository<T, ID> baseRepository;

    @Override
    public ID getIdValue(T entity) {
        //先获取主键属性名
        Field[] fields = entity.getClass().getDeclaredFields();
        String idField = null;
        for(Field field : fields){
            Annotation idAnno = field.getAnnotation(Id.class);//获取指定的注解
            if(null != idAnno){
                idField = field.getName();
                break;
            }
        }
        ID id = null;
        if(null != idField){
            //有注解@Id的，则先取 对应属性的值
            id = (ID) ReflectFieldService.getValueByKey(entity, idField);
        }
        return id;
    }

    /**
     * 查询（这是 实现类，函数 和 入参 都依赖于CommonService里面定义的）
     *
     * @param id
     * @return
     */
    @Override
    public T get(ID id) {
        T entity = this.baseRepository.getOne(id);
        return entity;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public T find(ID id) {
        T entity = this.baseRepository.findOne(id);
        //return this.baseRepository.findById(id).get();
        return entity;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public Result<T> delete(ID id) {
        this.baseRepository.delete(id);
        return new Result<>(Result.SUCCESS, "删除成功！", null, null);
    }

    @Override
    public Result<T> delete(T entity) {
        this.baseRepository.delete(entity);
        return Result.success("删除成功!", entity);
    }

    /**
     * 创建
     *
     * @param entity
     * @return
     */
    @Override
    public Result<T> create(T entity) {
        if(ObjectUtils.equals(entity, null)){
            return Result.fail("数据为空,无法创建!");
        }
        /*if(this.exists(entity.)){
            return Result.fail("实体id相同,无法重复创建!");
        }*/

        entity = this.baseRepository.saveAndFlush(entity);

        if(ObjectUtils.equals(entity, null)){
            return Result.fail("更新成功，但返回了null");
        }
        return Result.success(null, entity);
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @Override
    public Result<T> update(T entity) {
        if(ObjectUtils.equals(entity, null)){
            return Result.fail("数据为空,无法创建!");
        }

        /*if(!this.exists(entity.getId())){
            return Result.fail("数据库不存在该数据,无法执行更新");
        }*/

        T source= this.baseRepository.findOne(this.getIdValue(entity));
        BeanUtil.copyNullProperties(source, entity);

        entity = this.baseRepository.saveAndFlush(entity);

        if(ObjectUtils.equals(entity, null)){
            return Result.fail("更新成功，但返回了null");
        }
        return Result.success(null, entity);
    }

    /**
     * 读取所有
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<T> page(Pageable pageable) {
        return null;
    }

    /**
     * 判断id是否存在
     *
     * @param id
     * @return
     */
    @Override
    public boolean exists(ID id) {
        return this.baseRepository.exists(id);
    }
}
