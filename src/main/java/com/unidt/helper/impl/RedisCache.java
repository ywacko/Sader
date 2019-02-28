package com.unidt.helper.impl;

import com.unidt.helper.interf.ICache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCache  implements ICache{

    JedisPool pool = null;

    private static final int  DB_INDEX = 0;
    private static final int  TIME_OUT = 10000;
    @Override
    public boolean connect(String host, int port, String user, String pwd) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(32);
        config.setMaxIdle(8);
        config.setMaxWaitMillis(TIME_OUT);

        pool = new JedisPool(config,host, port,TIME_OUT, pwd,DB_INDEX);
        return true;
    }

    @Override
    public void close() {
        pool.close();
    }

    @Override
    public void set(String key, Object value) {
        Jedis redis = pool.getResource();
        redis.set(key, (String) value);
        redis.close();
    }

    @Override
    public Object get(String key) {
        Jedis redis = pool.getResource();
        String ret =  redis.get(key);
        redis.close();
        return ret;
    }

    @Override
    public void del(String key) {
        Jedis redis = pool.getResource();
        redis.del(key);
        redis.close();
    }

    @Override
    public boolean exists(String key) {
        Jedis redis = pool.getResource();
        boolean ret = redis.exists(key);
        redis.close();
        return ret;
    }

    @Override
    public void incr(String key) {
        Jedis redis = pool.getResource();
        redis.incr(key);
        redis.close();
    }

    @Override
    public void lpush(String key, Object value) {
        Jedis redis = pool.getResource();
        redis.lpush(key,(String)value);
        redis.close();
    }

    @Override
    public void rpush(String key, Object value) {
        Jedis redis = pool.getResource();
        redis.rpush(key,(String)value);
        redis.close();
    }

    @Override
    public Object lpop(String key) {
        Jedis redis = pool.getResource();
        Object ret = redis.lpop(key);
        return ret;
    }

    @Override
    public Object rpop(String key) {
        Jedis redis = pool.getResource();
        Object ret = redis.rpop(key);
        redis.close();
        return ret;
    }

    @Override
    public void expire(String key, int timecount) {
        Jedis redis = pool.getResource();
        redis.expire(key, timecount);
        redis.close();
    }
}
