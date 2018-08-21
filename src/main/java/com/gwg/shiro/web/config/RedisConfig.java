package com.gwg.shiro.web.config;

import com.gwg.shiro.web.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author laijianbo 2017年10月9日 上午9:39:10
 *
 */
@Configuration
@EnableCaching
public class RedisConfig {

	@Value("redis.sentinel.master")
	private String master;
    @Value("redis.sentinel.nodes")
	private String nodes;
	@Value("redis.password")
	private String password;

	/**
	 * Redis哨兵模式配置
	 * @return
	 */
	@Bean
	public RedisSentinelConfiguration sentinelConfig(){
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
		sentinelConfig.setMaster(master);
		sentinelConfig.setSentinels(RedisUtil.createSentinels(nodes));
		return sentinelConfig();
	}


	/**
	 * 注入 RedisConnectionFactory
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory(){
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(sentinelConfig(), new JedisPoolConfig());
		redisConnectionFactory.setPassword(password);
		return redisConnectionFactory;
	}

	/**
	 * 实例化 RedisTemplate 对象
	 *
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String,Object>();
		initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

		cacheManager.setDefaultExpiration(21600);
	    // 设置缓存的过期时间
	    Map<String, Long> expires = new HashMap<String, Long>();
	    expires.put("seat-dicContent", 1000L);
	    expires.put("user-role", 300L);
	    expires.put("basic-info", 300L);
		expires.put("personal-center", 300L);
	    cacheManager.setExpires(expires);

		return cacheManager;
	}

	/**
	 * 设置数据存入 redis 的序列化方式
	 *
	 * @param redisTemplate
	 * @param factory
	 */
	private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(factory);
	}

}