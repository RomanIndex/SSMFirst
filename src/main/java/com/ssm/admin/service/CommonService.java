package com.ssm.admin.service;

import com.ssm.admin.entity.SsmBaseEntity;
import com.ssm.base.view.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *  1、定义 公共类 相关的 顶级 接口，泛型参数
 * @param <T>
 * @param <ID>
 */
public interface CommonService<T extends SsmBaseEntity, ID> {


    /* 从实体类 获取 主键（实体类有@id标签的） 的值*/
    ID getIdValue(T entity);

    /**
     * 查询
     * @param id
     * @return
     */
    T get(ID id);

    /**
     * 查询
     * @param id
     * @return
     */
    T find(ID id);

    /**
     * 删除
     * @param id
     * @return
     */
    Result<T> delete(ID id);

    Result<T> delete(T entity);

    /**
     * 创建
     * @param entity
     * @return
     */
    Result<T> create(T entity);

    /**
     * 更新
     * @param entity
     * @return
     */
    Result<T> update(T entity);

    /**
     * 读取所有
     * @param pageable
     * @return
     */
    Page<T> page(Pageable pageable);

    /**
     * 判断id是否存在
     * @param id
     * @return
     */
    boolean exists(ID id);
}
