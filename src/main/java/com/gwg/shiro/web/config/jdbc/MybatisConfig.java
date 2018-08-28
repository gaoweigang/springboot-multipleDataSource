package com.gwg.shiro.web.config.jdbc;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * 注释
 */
//@Configuration
@Deprecated
public class MybatisConfig {

    /**
     * 生成一个名字为 sqlSessionFactory 的bean
     * mybatis的sqlSessionFactory配置
     *
     * 等价于
     * mybatis:
     *     mapperLocations: classpath*:com/gwg/shiro/web/mapper/*.xml
     *     typeAliasesPackage: com.gwg.shiro.web.model
     *
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:com/gwg/shiro/web/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 生成一个名字为mapperScannerConfigurer的bean
     * mapper接口扫描包
     *
     * 等价于
     * @MapperScan("com.gwg.shiro.web.mapper")
     */
    @Bean
    @Order
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage("com.gwg.shiro.web.mapper");
        return configurer;
    }

}
