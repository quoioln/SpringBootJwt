package com.vpquoi.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class Application {

  private final Log logger = LogFactory.getLog(getClass());

  @Autowired private ApplicationContext applicationContext;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @PreDestroy
  public void cleanup() {
    logger.info("Closing application context..let's do the final resource cleanup");
    logger.info("Clean up redis...");
    Jedis jedis = applicationContext.getBean(Jedis.class);
    if (jedis != null) {
      jedis.close();
    }
    logger.info("Clean up redis pool...");
    JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
    if (jedisPool != null) {
      jedisPool.close();
    }
  }
}
