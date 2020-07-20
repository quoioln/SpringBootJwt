package com.vpquoi.example.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PreDestroy;

@Configuration
public class ApplicationConfig {

    private final Log logger = LogFactory.getLog(getClass());

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.timeout}")
    private int redisTimeout;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.db_name}")
    private int redisDbName;

    @Bean
    public Jedis initializeJedis() {
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort, redisTimeout, redisPassword, redisDbName);
        return jedisPool.getResource();
    }

}
