----------结合 正则的 字符串 分割（结果 行转列）
--第一种：

select regexp_substr('a|b|c', '[^|]+', 1, rownum) aaa
  from dual
connect by rownum <= length(regexp_replace('a|b|c', '[^|]', null)) + 1

--第二种：

SELECT REGEXP_SUBSTR ('abc1,cbd2,db3,db5', '[^,]+', 1,rownum) aaa
  FROM DUAL
  CONNECT BY ROWNUM <= LENGTH ('abc1,cbd2,db3,db5') - LENGTH (REPLACE ('abc1,cbd2,db3,db5', ',', '')) + 1

/*其中UNION(A U B)是取A以及B的所有信息，对于A和B的相同的数据只取一次。
其中UNION ALL(A U+ B)取A以及B的所有信息，对于A和B的相同的数据各取一次。
其中INTERSECT(A n B)只取A和B相同的部分，只取一次。
其中MINUS(A - B)只取属于A但不属于B的部分。*/

------------切割字符串（行转列）----------------------
select *
  from (SELECT *
          FROM (select REGEXP_SUBSTR('1#2#3#3#3', '[^#]+', 1, rownum) as position
                  from dual
                connect by rownum <= REGEXP_COUNT('01#02#03#04#05', '[^#]+'))
         ORDER BY position)
MINUS
SELECT '3' FROM dual

-------应用1：获取最大code-----------------------------------
select to_number(DIGITCODE)
  from (select ROW_NUMBER() over(order by to_number(REGEXP_REPLACE(A.TIME_CTR_CODE, '[^0-9]')) desc) as CODESEQ,
               A.TIME_CTR_CODE,
               REGEXP_REPLACE(A.TIME_CTR_CODE, '[^0-9]') DIGITCODE
          from STUDIO_TIME_CONTROL A)
 where CODESEQ = 1

------应用2：获取包含 某个标签的 所有标签（数据库标签以 | 存储隔开）---------------------------------
select *
  from base_tag t
 where t.tag_no in
       (select regexp_substr('TAG10021|TAG10010', '[^|]+', 1, rownum) aaa
          from dual
        connect by rownum <= length(regexp_replace('TAG10002|TAG10003', '[^|]', null)) + 1)
        
------应用2，还有一种解决方案（结合mybatis的if foreach）：
<if test="query.tagNos != null and query.tagNos.length > 0">
   and 
   <foreach collection="query.tagNos" index="index" item="val" separator="or" open="(" close=")">
    INSTR('|'||st.tags_no||'|', '|'||#{val}||'|') > 0
   </foreach>
</if>