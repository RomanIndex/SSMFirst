package com.ssm.common.multiDataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect    // for aop
@Component // for auto scan
@Order(0)  // execute before @Transactiona
public class DataSourceAspect {
    /* maven一共引入了四个，开始一直进不来*/
    @Pointcut("execution(* com.ssm.*.dao..*.*(..)) || execution(public * com.ssm.*.daoJpa..*.*(..))")
    public void dataSourceSwitch(){}

    @Before("dataSourceSwitch()")
    public void before(JoinPoint jp) {
        //System.out.println("@Before getSignature："+ jp.getSignature().getName());//调用的方法名
        //System.out.println("@Before getTarget："+ jp.getTarget().getClass().getName());//@4679554d 这种"类实例？"
        /**
         * dao层继承接口方式：根据连接点所属的 类实例，动态切换数据源
         * 另外，还有一种 注解 的方式，但是没有接口的细粒，简单方便，配置也相对麻烦一点
         */
        if (jp.getTarget() instanceof wwMysqlMapper) {
            DataSourceTypeManager.set(DataSourceEnum.wwMysql);
        } else if(jp.getTarget() instanceof wwOracleMapper){
            DataSourceTypeManager.set(DataSourceEnum.wwOracle);
        }else {
            //当然，这一步也可以不写，因为defaultTargertDataSource就是该类所用的aliMysql
            DataSourceTypeManager.set(DataSourceEnum.aliMysql);
        }
    }
}
