package test;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.interf.IFraDB;
import org.bson.Document;
import org.bson.conversions.Bson;

public class IndexCreate {
    public static void createIndex() throws Exception {
        IFraDB db = DBFactory.createMongoDB();

        IndexOptions options = new IndexOptions().unique(true);
        //
        // 用户表 user_id 添加唯一约束
        db.collection("t_user").createIndex(Indexes.ascending("user_id"), options);
        //
        // user_id, product_id 唯一
        db.collection("t_user_product").createIndex(Indexes.ascending("user_id","product_id"), options );

    }
    public static void main(String[] args) throws Exception {

    }
}