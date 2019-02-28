package com.unidt.helper.interf;

public interface ICache {

    /**
     *
     * @return
     */
    boolean connect(String host, int port, String user, String pwd);

    /**
     *
     */
    void close();

    /**
     *
     * @param key
     * @param value
     */
    void   set(String key, Object value);

    /**
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     *
     * @param key
     */
    void del(String key);

    /**
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     *
     * @param key
     */
    void   incr(String key);

    /**
     * 列表操作
     * @param key
     * @param value
     */
    void lpush(String key, Object value);

    /**
     *
     * @param key
     * @param value
     */
    void rpush(String key, Object value);

    /**
     *
     * @param key
     * @return
     */
    Object lpop(String key);

    /**
     *
     * @param key
     * @return
     */
    Object rpop(String key);

    /**
     * 设定key的超时时间，超时后该键被删除
     * @param key
     * @param timecount, 单位为秒
     */
    void expire(String key, int timecount);
}
