-- WHERE 和 HAVING 的区别

-- WHERE：是在执行GROUP BY操作之前进行的过滤，表示从全部数据之中筛选出部分的数据，在WHERE之中不能使用统计函数；
-- HAVING：是在GROUP BY分组之后的再次过滤，可以在HAVING子句中使用统计函数；