package com.vpquoi.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

public interface RedisClientService {

    boolean sadd(String key, String value);

    boolean srem(String key, String value);

    boolean sismember(String key, String value);

    boolean existed(String key);
}
