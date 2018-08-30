package com.gwg.shiro.web.config.jdbc;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切面
 */
@Aspect // for aop
@Component // for auto scan
@Order(0) // execute before @Transactional
public class MultipleDataSourceAspectAdvice {

    private static Logger logger = LoggerFactory.getLogger(MultipleDataSourceAspectAdvice.class);


    /**
     * 切入点对连接点进行拦截的定义,该方法无方法体,主要为方便该类中其他方法使用此处配置的切入点,该方法体内容不会执行
     * 说明：包com.gwg.shiro.web.mapper中所有的类已select为前缀的方法
     */
    @Pointcut("execution(public * com.gwg.shiro.web.dao.*.add*(..))")
    public void writeMethod() {
    };

    /**
     *切入点对连接点进行拦截的定义,该方法无方法体,主要为方便该类中其他方法使用此处配置的切入点。该方法体内容不会执行
     * 说明：包com.gwg.shiro.web.mapper中所有的类已select为前缀的方法
     */
    @Pointcut("execution(public * com.gwg.shiro.web.dao.*.query*(..))")
    public void readMethod() {
    };


    /**
     * 前置通知
     * advice通知：所谓通知指的就是指拦截到连接点之后要执行的代码，通知分为前置、后置、异常、最终、环绕通知五类
     */
    @Before("writeMethod()")
    public void beforeWrite(JoinPoint jp) {//参数是连接点
        logger.info("execute beforeinv start ...........");

        Object[] args = jp.getArgs();
        logger.info("mapper里各个方法的参数：{}", (args != null? args[0] : null));//连接点接收的参数，在这里就是UserMapper里面的各个方法的参数
        if(args==null){
            DataSourceHolder.setDataSourceType(DataSourceType.SLAVE.getCode());
            return;
        }
        DataSourceHolder.setDataSourceType(DataSourceType.MASTER.getCode());
    }

    /**
     * 前置通知
     * advice通知：所谓通知指的就是指拦截到连接点之后要执行的代码，通知分为前置、后置、异常、最终、环绕通知五类
     */
    @Before("readMethod()")
    public void beforeRead(JoinPoint jp) {
        logger.info("beforeRead，mapper里各个方法的参数：{}", (jp.getArgs() != null? jp.getArgs()[0] : null));//连接点接收的参数，在这里就是UserMapper里面的各个方法的参数
        DataSourceHolder.setDataSourceType(DataSourceType.SLAVE.getCode());
    }

}