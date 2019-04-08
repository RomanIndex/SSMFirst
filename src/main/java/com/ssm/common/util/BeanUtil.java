package com.ssm.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BeanUtil {

    /**
     * 将目标源中不为空的字段过滤，将数据库中查出的数据源复制到提交的目标源中
     *
     * @param source 用id从数据库中查出来的数据源
     * @param target 提交的实体，目标源
     */
    public static void copyNullProperties(Object source, Object target) {
        /**
         * copyProperties：该方法就是将source的属性值复制到target中
         *
         * source 数据源（也就是我们通过id去数据库查询出来的数据）
         * target 目标源（也就是我们请求更新的数据）
         * ignoreProperties (需要过滤的字段)
         */
        BeanUtils.copyProperties(source, target, getNoNullProperties(target));
    }

    /* ------------目标源中不为空的 字段 取出（为解决JPA更新空值的问题）------------------------ */
    private static String[] getNoNullProperties(Object target) {
        BeanWrapper srcBean = new BeanWrapperImpl(target);//注意，引入的包是org.springframework.beans.BeanWrapper
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value != null) noEmptyName.add(p.getName());
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }
}
