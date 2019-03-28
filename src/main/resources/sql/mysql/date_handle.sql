-- str 转 date
STR_TO_DATE(CONCAT_WS('-', #{year}, #{month}, 1),'%Y-%m-%d %H')

-- 一个月后
date_add(STR_TO_DATE(CONCAT_WS('-', #{year}, #{month}, 1), '%Y-%m-%d %H'), interval 1 MONTH)