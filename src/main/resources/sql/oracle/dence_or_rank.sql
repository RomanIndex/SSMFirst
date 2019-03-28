--【区别】dence_rank在并列关系时，相关等级不会跳过。rank则跳过.

--rank()是 跳跃排序，有两个第二名时接下来就是第四名（同样是在各个分组内） eg：1 2 2 4 5
--dense_rank()是 连续排序，有两个第二名时仍然跟着第三名。eg：1 2 2 3 4
--row_number这个函数不需要考虑是否并列，那怕根据条件查询出来的数值相同也会进行连续排名 eg：1 2 3 4 5

--partition by用于给结果集进行分区。partition by 用于结果集分组，如果没有指定，会把整个结果集作为一个分组

--partition by和group by有何区别？
--partition by只是将原始数据进行名次排列(记录数不变)

--group by是对原始数据进行聚合统计(记录数可能变少, 每组返回一条)

--rank 、dense_rank 、row_numer 都是不同方式的结果集组内排序，一般都结合over 字句出现，over 字句里 会有 partition by、order by、last、first 的任意组合，如下：
--rank() over(partition by a order by b nulls first)

--使用rank over()的时候，空值是最大的，如果排序字段为null, 可能造成null字段排在最前面，影响排序结果。

--可以这样： rank over(partition by course order by score desc nulls last)//关键：nulls last