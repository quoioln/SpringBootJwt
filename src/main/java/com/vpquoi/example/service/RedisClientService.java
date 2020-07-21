package com.vpquoi.example.service;

public interface RedisClientService {

    boolean sadd(String key, String value);

    boolean srem(String key, String value);

    boolean sismember(String key, String value);

    boolean existed(String key);
}
