------查重---------医生重名
select h.hospital_name 医院, t.team_name 队伍, m.mobile 电话,m.name 名字,m.position 职位,decode(m.user_type,1,'导师',2,'领队',3,'队员','') 队伍身份
  from mc_member m
  left join mc_team t on t.id = m.team_id
  left join mc_hospital h on h.hospital_id = m.hospital_id
 where m.status = 1
   and m.name in (select im.name
                          from mc_member im
                         where im.status = 1
                         group by im.name
                        having count(*) > 1)
 order by m.name --for update
 
-----删重思路-----------------
delete from satisfaction_survey s
 where s.as_side = 0
 and(s.project_no/*, to_char(s.submit_time, 'yyyy-mm-dd')*/) in
       (select ss.project_no/*, to_char(ss.submit_time, 'yyyy-mm-dd') */
       from satisfaction_survey ss
       where ss.as_side=0
       group by ss.project_no/*, to_char(ss.submit_time, 'yyyy-mm-dd')*/
      having count(*) > 1)
   and rowid not in
       (select max(rowid) from satisfaction_survey where as_side=0 group by project_no/*, to_char(submit_time, 'yyyy-mm-dd')*/ having count(*) > 1)
       
--HAVING子句是SELECT语句的可选子句。它用于过滤由GROUP BY子句返回的行分组。 这就是为什么HAVING子句通常与GROUP BY子句一起使用的原因
--请注意，HAVING子句过滤分组的行，而WHERE子句过滤行。这是HAVING和WHERE子句之间的主要区别。
----（row_number()等是 排序函数，而group by、having是统计函数）
SELECT
    column_list
FROM
    T
GROUP BY
    c1
HAVING
    group_condition;