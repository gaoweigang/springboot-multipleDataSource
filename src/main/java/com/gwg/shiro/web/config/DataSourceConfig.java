package com.gwg.shiro.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * MyBatis基础配置
 * @author Administrator
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig{

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("mysql.dbdriver")
	private String driverClassName;
	@Value("mysql.dburl")
	private String dburl;
	@Value("mysql.dbUserName")
	private String username;
	@Value("mysql.password")
	private String password;
	@Value("mysql.initSize")
	private int initialSize;
	@Value("mysql.maxActive")
	private int maxActive;
	@Value("mysql.minEvictableIdleTimeMillis")
	private long minEvictableIdleTimeMillis;
	@Value("mysql.testOnReturn")
	private boolean testOnBorrow;
	@Value("mysql.testOnReturn")
	private boolean testOnReturn;
	@Value("mysql.validationQuery")
	private String validationQuery;



	/**
	 * 
	 * @Title: 生成一个名字为  dataSource的bean
	 * @Description: 数据源的配置  
	 * @param: @return      
	 * @return: DataSource      
	 * @throws
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("driverClassName:{}, dburl:{}, username:{}, password:{}", driverClassName, dburl, username, password);
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driverClassName);
		druidDataSource.setUrl(dburl);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setValidationQuery(validationQuery);
		return druidDataSource;

	}
	
	/**
	 * 生成一个名字为 sqlSessionFactory 的bean
	 * mybatis的sqlSessionFactory配置
	 * @param dataSource
	 */
	@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
    	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    	bean.setDataSource(dataSource);
    	bean.setTypeAliasesPackage("com.gwg.shiro.web.model");
    	
    	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(); 
    	try {
			bean.setMapperLocations(resolver.getResources("classpath*:com/gwg/shiro/web/mapper/*.xml"));
		} catch (IOException e) {
			e.printStackTrace();	
		}
    	return bean.getObject();
    }
	
    
 /*   @Bean
    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
    	return new SqlSessionTemplate(sqlSessionFactory);
    }
    */
	
	/**
	 * 生成一个名字为mapperScannerConfigurer的bean
	 * mapper接口扫描包
	 */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		configurer.setBasePackage("com.gwg.shiro.web.mapper");
		return configurer;
	}
	
    
    /**
	 * 
	 * @Title: transactionManager   
	 * @Description: 配置事务管理器
	 * @param: @param dataSource
	 * @return: DataSourceTransactionManager      
	 */
/*	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource)
			throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}
*/

}
