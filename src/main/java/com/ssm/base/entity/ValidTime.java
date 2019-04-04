package com.ssm.base.entity;

public class ValidTime {
    /**
     * 有效时间 类，设计思路：
     *
     * 1、循环型。没有首尾时间限制
     * 推荐语法：cycle .. to ..
     *
     * 2、时段型。必须有首尾时间点限制
     * 推荐语法：between .. add ..+N(s)
     *
     * 3、定点型。起始生效 或 截止生效时间
     * 推荐语法：moment before/after ..
     *
     * 实际一定是 以上三种 混合使用
     * 场景一、周末有效（时段 + 循环）
     * 场景二、每月最后一天有效（同上）
     * 场景三、早九晚五有效（几乎一样）
     */
}
