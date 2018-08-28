package com.gwg.shiro.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//因为我是在DataSourceConfig中自己配置的数据源，因此在此排查SpringBoot自动配置数据源
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.gwg.shiro.web.mapper")
/**
 * 启用Spring代理功能，在这里使用的是Cglib生成代理对象，默认使用的是Spring AOP生成代理对象
 * 如果使用Cglib来生成代理对象，需要指定需要指定编织时间，在这里使用静态织入，即编译时织入
 * 如果使用maven来构建项目，主要在maven中配置即可
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)//在
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
