package com.ssm.admin.service;

import com.ssm.admin.entity.SsmBaseEntity;
import com.ssm.base.view.Result;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *  1、定义 公共类 相关的 顶级 接口，泛型参数
 * @param <T>
 * @param <ID>
 */
public interface CommonService<T extends SsmBaseEntity, ID> {


    /**
     * 从实体类 获取 主键（实体类有@id标签的） 的值
    */
    ID getIdValue(T entity);

    /**
     * 查询
     */
    T getById(ID id);

    T getByExample(T entity);

    List<T> listByExample(Example<T> example);

    List<T> selectAllById(Iterable it);

    List<T> selectAll();

    /**
     * 删除
     */
    Result<?> deleteById(ID id);

    Result<?> delete(T entity);

    /**
     * 创建
     */
    Result<?> create(T entity);

    Result<?> batchCreate(List<T> list);

    /**
     * 更新
     */
    Result<?> update(T entity);

    /**
     *  分页查询
     */
    Page<T> page(Pageable pageable);

    /**
     * 判断id是否存在
     */
    boolean existsById(ID id);
}
