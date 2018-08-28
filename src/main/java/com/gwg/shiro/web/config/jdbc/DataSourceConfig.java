package com.gwg.shiro.web.config.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MyBatis基础配置
 * @author Administrator
 *
 */
@Configuration
@EnableTransactionManagement //启用Spring Aop事物管理,启用之后就可以使用@Transactional注解了
public class DataSourceConfig{

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	//数据源1
	@Bean(name = "dataSourceMaster")
	@ConfigurationProperties(prefix = "spring.datasource.master") // application.properteis中对应属性的前缀
	public DataSource dataSourceMaster() {
		return DataSourceBuilder.create().build();
	}

	//数据源2
	@Bean(name = "dataSourceSlave")
	@ConfigurationProperties(prefix = "spring.datasource.slave") // application.properteis中对应属性的前缀
	public DataSource dataSourceSlave() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 动态数据源: 通过AOP在不同数据源之间动态切换
	 * @return
	 */
	@Primary
	@Bean(name = "dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 默认数据源
		dynamicDataSource.setDefaultTargetDataSource(dataSourceSlave());
		// 配置多数据源
		Map<Object, Object> dsMap = new HashMap();
		dsMap.put(DataSourceType.MASTER.getCode(), dataSourceMaster());
		dsMap.put(DataSourceType.SLAVE.getCode(), dataSourceSlave());

		dynamicDataSource.setTargetDataSources(dsMap);
		return dynamicDataSource;
	}

	/**
	 * 配置@Transactional注解事物
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}

}
