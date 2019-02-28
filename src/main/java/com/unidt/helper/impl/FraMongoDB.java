package com.unidt.helper.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.unidt.exception.MongoException;
import com.unidt.helper.interf.IFraDB;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 操作MongoDB方法封装
 */
public class FraMongoDB<TDoc, TResult> implements IFraDB<TDoc, TResult> {
    private MongoClient mongoClient = null;
    private String dbname = null;
    private String collection = null;
    private Logger log = LoggerFactory.getLogger(FraMongoDB.class);

    //
    // 为避免共享资源（collection）冲突，加锁
    private ReadWriteLock  readWriteLock = new ReentrantReadWriteLock();


    public IFraDB<TDoc, TResult> setDbname(String db) {
        this.dbname = db;
        return this;
    }

    @Override
    /**
     * 当有写操作进行时，不允许更改正在操作的表名，以免冲突
     *
     * 在高并发情况下由于多余锁的存在，可能会存在性能问题
     */
    public IFraDB<TDoc, TResult> setCollection(String coll) {
        readWriteLock.writeLock().lock();
        collection = coll;
        readWriteLock.writeLock().unlock();
        return this;
    }

    @Override
    public MongoCollection<Document> collection(String coll) {
        return mongoClient.getDatabase(dbname).getCollection(coll);
    }

    @Override
    public Object getRawDB() {
        return this;
    }

    @Override
    public TResult executeQuery(TDoc sql) {
        log.error("不支持的数据库操作");
        return null;
    }

    @Override
    public int execute(TDoc sql) {
        log.error("不支持的数据库操作");
        return 0;
    }

    @Override
    public void executeBatch(List<TDoc> sqls) {
        log.error("不支持的数据库操作");
    }

    @Override
    /**
     * 带用户名密码连接Mongodb数据库
     */
    public boolean connect(String host, int port, String username, String pwd) throws Exception {
        MongoCredential credential = MongoCredential.createScramSha1Credential(username,"admin", pwd.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(host,port), Collections.singletonList(credential));
        return true;
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    @Override
    public TResult find() throws Exception {
        log.debug("find all results: " + collection);
        checkClientStatus();
        readWriteLock.readLock().lock();
        TResult result = (TResult) collection(collection).find();
        readWriteLock.readLock().unlock();

        return result;
    }

    @Override
    public TResult find(TDoc filter) throws Exception {
        log.debug("find  results: " + collection);
        checkClientStatus();
        readWriteLock.readLock().lock();
        TResult result = (TResult) collection(collection).find((Bson) filter);
        readWriteLock.readLock().unlock();
        return null;
    }

    @Override
    public long delete(TDoc filter) throws Exception {

        checkClientStatus();
        readWriteLock.writeLock().lock();
        DeleteResult result = collection(collection).deleteMany((Bson)filter);
        readWriteLock.writeLock().unlock();

        log.debug("Delete Records," + result.getDeletedCount() + " items has been deleted");
        return result.getDeletedCount();
    }

    @Override
    public long update(TDoc filter, TDoc value) throws Exception {

        checkClientStatus();
        readWriteLock.writeLock().lock();
        UpdateResult result = collection(collection).updateMany((Bson)filter,(Bson)value);
        readWriteLock.writeLock().unlock();

        log.debug("Update Records: " + result.getModifiedCount() + " items has been modified");
        return result.getModifiedCount();
    }

    @Override
    public void insertOne(TDoc tDoc) throws Exception {
        checkClientStatus();
        readWriteLock.writeLock().lock();
        collection(collection).insertOne((Document) tDoc);
        readWriteLock.writeLock().unlock();
    }

    @Override
    public void insertMany(List<TDoc> tDocs) throws Exception {
        checkClientStatus();
        readWriteLock.writeLock().lock();
        collection(collection).insertMany((List<? extends Document>) tDocs);
        readWriteLock.writeLock().unlock();
    }

    public void createAscendingIndex(String db, String coll, List<String> keys) {
        MongoCollection<Document> collection = mongoClient.getDatabase(db).getCollection(coll);
        collection.createIndex(Indexes.ascending(keys));
    }
    public void createTextIndex(String db, String coll, String key) {
        mongoClient.getDatabase(db).getCollection(coll).createIndex(Indexes.text(key));
    }

    public void createHashIndex(String db, String coll, String key) {
        mongoClient.getDatabase(db).getCollection(coll).createIndex(Indexes.hashed(key));
    }

    private void checkClientStatus() throws  MongoException{
        if (mongoClient == null) {
            throw  new MongoException("MongoServer not connected");
        }
    }

}
