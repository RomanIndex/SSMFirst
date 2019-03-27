--------按天统计数据库数据--------------------
select sum(num) from(
----------------按 连续的天 统计，没有的统计为0------(下面三个已校验过，可copy，时间区间-前后-都是闭区间)-----
with date_split as(
--select trunc((select max(create_time) from  STUDIO_CHAT)) - (level - 1) dt from dual connect by level <= (select (trunc(max(create_time)) - trunc(min(create_time))) from STUDIO_CHAT) + 1--按表已有时间统计
--select trunc(sysdate) - (level - 1) dt from dual connect by level <= (select trunc(sysdate) - trunc(min(create_time)) from STUDIO_CHAT) + 1--从当前时间统计
--select trunc(to_date('2018-08-31', 'yyyy-mm-dd')) - (level - 1) dt from dual connect by level <= (select trunc(to_date('2018-08-31', 'yyyy-mm-dd')) - trunc(to_date('2018-08-01', 'yyyy-mm-dd')) from dual) + 1--按传入的时间段统计
)
select d.dt || '至' || (d.dt + 1) time_piece, count(case when i.create_time is not null then 1 else null end) as num
  from date_split d
  left join STUDIO_CHAT i on i.create_time < d.dt + 1 and i.create_time >= d.dt
 group by d.dt
 order by d.dt desc

)

-----------按 一天内 的时段 统计-------------------
select sum(num) from(
--统计 时间段 改变时，需要修改的地方：1、level分时间段；2、connect by后的数量；3、decode条件两个位置
select to_char(d.dt, 'hh24:mi:ss') || ' 至 ' ||to_char(d.dt + 1/24, 'hh24:mi:ss') time_piece, count(case when i.create_time is not null then 1 else null end) as num
  from (select (trunc(sysdate) - (level/24)) dt from dual connect by level <= 24) d
  left join STUDIO_CHAT i on to_char(i.create_time, 'hh24:mi:ss') >= to_char(d.dt, 'hh24:mi:ss') 
                         and to_char(i.create_time, 'hh24:mi:ss') < decode(to_char(d.dt + 1/24, 'hh24:mi:ss'), '00:00:00', '23:59:59',to_char(d.dt + 1/24, 'hh24:mi:ss'))
 group by d.dt
 order by d.dt desc

)