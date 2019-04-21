package com.ssm.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Java8Util {

    /**
     * distinct（）不提供按照属性对对象列表进行去重的直接实现
     * 它是基于hashCode（）和equals（）工作的
     *
     * 我们想要按照对象的属性，对对象列表进行去重，我们可以通过下面的方法来实现
     * 下面的方法可以被Stream接口的 filter()接收为参数，借助filter方法实现
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
