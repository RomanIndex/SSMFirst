and trunc(sv.create_time) = trunc(sysdate) ------同一天的数据

select trunc(max(sc.create_time)), trunc(min(sc.create_time)) from STUDIO_CHAT sc order by sc.create_time ----数据相隔的天数

select (trunc(sysdate) - (level/24)) dt from dual connect by level <= 6 -- 从当前时间开始，以 每小时 为间隔分6次
select level, (trunc(sysdate) - (level/24)) dt from dual connect by level <= 24 --按1小时，分隔一天

select (trunc(sysdate) - (level*7)) dt from dual connect by level <= 6 --从当前时间开始，以 每星期（七天） 为间隔分6次

select sysdate - (1/24) from dual -------取当前 时间 前一个小时的时间

select level from dual connect by level < 24 -------简单明了，知道connect by的作用

select to_date('14:31:36', 'hh24:mi:ss') from dual -- 结果：2018/10/1 14:31:36 **时分秒 转 时间，默认会加上 年月日

select * from STUDIO_CHAT where trunc(create_time) = to_date('2018-08-24', 'yyyy-mm-dd') --查看 具体一天的 数据（trunc处理的date返回也是date类型数据）

------根据时间 判断 活动当前 活动状态（未开始、进行中、已结束）------------
select *
  from (select case when a.start_time > sysdate then 0 when a.end_time < sysdate then 2 else 1 end as aaaaa,
               case when a.display_start_time > sysdate then 0 when a.display_end_time < sysdate then 2 else 1 end bbbb,
               a.*
          from qxy_activity a
        
        ) a
 where a.status = 1
   and a.bbbb = 1