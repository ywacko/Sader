package com.unidt.helper.interf;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

/**
 *
 * @param <TDoc>, 传递给数据库的参数类型， 对mongodb来说是Document,对关系型数据库而言是sql
 * @param <TResult> 返回的数据类型
 */
public interface IFraDB<TDoc, TResult> {

    /**
     * 该函数针对关系型数据库
     * @param sql 要执行的SQL查询语句
     * @return
     */
    TResult executeQuery(TDoc sql);

    /**
     * 执行SQL语句，返回影响的函数
     * @param sql 要执行的SQL语句
     * @return
     */
    int execute(TDoc sql);

    void executeBatch(List<TDoc> sqls);
    /**
     *
     * @param host
     * @param port
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    boolean connect(String host, int port, String username, String pwd) throws  Exception;

    /**
     * 关闭连接，释放资源
     */
    void close();

    /**
     *
     * @return TResult, 不同类型的数据库，返回值不同，此处采用模板来进行适配
     * @throws Exception
     */
    TResult find() throws Exception;

    /**
     *
     * @param filter 过滤参数，对mongodb数据库来说，参数类型是Bson
     * @return
     * @throws Exception
     */
    TResult find(TDoc filter) throws Exception;

    /**
     *
     * @param filter
     * @return
     * @throws Exception
     */
    long delete(TDoc filter) throws  Exception;

    /**
     * 更新，此方法会更新所有符合filter指定规则的数据
     * @param filter 因为update方法稍有不同，此处采用Object作为参数类型
     * @param value 修改后的值
     * @return
     * @throws Exception
     */
    long update(TDoc filter, TDoc value) throws  Exception;

    /**
     *
     * @param doc
     * @throws Exception
     */
    void insertOne(TDoc doc) throws Exception;

    /**
     *
     * @param docs
     * @throws Exception
     */
    void insertMany(List<TDoc> docs) throws Exception;

    /**
     *  设置数据库名称
     * @param db
     */
    IFraDB<TDoc, TResult> setDbname(String db);

    /**
     * 设置表名，如果某一操作针对不同表进行，则此函数失效
     * @param coll
     */
    IFraDB<TDoc, TResult> setCollection(String coll);


    /**
     * 为提升性能，针对mongodb直接操作collection，不需要在客户应用中使用锁，
     * 由此带来的弊端是无法监控用户操作
     * @param coll
     * @return
     */
    MongoCollection<Document> collection(String coll);
    /**
     * 获取源数据的操作对象
     * @return
     */
    Object getRawDB();
}
