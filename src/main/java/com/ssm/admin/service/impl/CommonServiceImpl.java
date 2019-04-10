package com.ssm.admin.service.impl;

import com.ssm.admin.entity.SsmBaseEntity;
import com.ssm.admin.service.CommonService;
import com.ssm.base.service.ReflectFieldService;
import com.ssm.base.view.Result;
import com.ssm.common.util.BeanUtil;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 2、公共 泛型接口 的实现类，
 * @param <T>
 * @param <ID>
 */

//@Service（加这个下面JpaRepository就报错，why？）
//@Transactional
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
     * 无论是 当个对象 还是 集合，不要返回null，返回空对象或集合，避免 空指针异常
     *
     */
    @Override
    public T getById(ID id) {
        //return this.baseRepository.findOne(id);
        Optional<T> optional = this.baseRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * findOne：返回实体的optional对象
     *
     * getOne：返回实体的引用，代理对象
     */
    @Override
    public T getByExample(T entity) {
        Example<T> example = Example.of(entity);
        //return this.baseRepository.findOne(example);
        Optional<T> optional = this.baseRepository.findOne(example);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<T> listByExample(T entity) {
        Example<T> example = Example.of(entity);
        List<T> list = this.baseRepository.findAll(example);
        return list == null ? new ArrayList<>() : list;
    }

    /**
     *  删除
     */
    @Override
    public Result<?> deleteById(ID id) {
        this.baseRepository.deleteById(id);
        //this.baseRepository.delete(id);
        return Result.success("删除成功!");
    }

    @Override
    public Result<?> delete(T entity) {
        this.baseRepository.delete(entity);
        return Result.success("删除成功!", entity);
    }

    /**
     * 创建
     */
    @Override
    public Result<?> create(T entity) {
        if(ObjectUtils.equals(entity, null)){
            return Result.fail("数据为空,无法创建!");
        }

        /* --如何快速定位问题：The given id must not be null! （改xml、实体类？）-- */
        /*if(this.existsById(this.getIdValue(entity))){
            return Result.fail("实体id相同，无法重复创建！");
        }*/

        //entity = this.baseRepository.saveAndFlush(entity);
        entity = this.baseRepository.save(entity);

        if(ObjectUtils.equals(entity, null)){
            return Result.fail("创建成功，但返回了null");
        }

        return Result.success(entity);
    }

    @Override
    public Result<?> batchCreate(List<T> list) {
        this.baseRepository.saveAll(list);
        //this.baseRepository.save(list);
        return Result.success("批量新增成功！（有失败的可以提示吗？）");
    }

    /**
     * 更新
     */
    @Override
    public Result<?> update(T entity) {
        if(ObjectUtils.equals(entity, null)){
            return Result.fail("数据为空,更新啥!");
        }

        if(!this.existsById(this.getIdValue(entity))){
            return Result.fail("数据库没查到该 主键 数据，更新终止！");
        }

        T source= this.baseRepository.getOne(this.getIdValue(entity));
        BeanUtil.copyNullProperties(source, entity);

        entity = this.baseRepository.saveAndFlush(entity);

        if(ObjectUtils.equals(entity, null)){
            return Result.fail("执行saveAndFlush，返回了null？");
        }

        return Result.success(entity);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<T> page(Pageable pageable) {
        return this.baseRepository.findAll(pageable);
    }

    /**
     * 指定 主键 的数据是否存在
     */
    @Override
    public boolean existsById(ID id) {
        return this.baseRepository.existsById(id);
        //return this.baseRepository.exists(id);
    }
}
