package com.vpquoi.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisClientServiceImpl implements RedisClientService {

  @Autowired private Jedis jedis;

  @Override
  public boolean sadd(String key, String value) {
    return jedis.sadd(key, value) == 1;
  }

  @Override
  public boolean srem(String key, String value) {
    return jedis.srem(key, value) == 1;
  }

  @Override
  public boolean sismember(String key, String value) {
    return jedis.sismember(key, value);
  }

  @Override
  public boolean existed(String key) {
    return jedis.exists(key);
  }
}
