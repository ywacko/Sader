package test;

import com.mongodb.DB;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.unidt.helper.conf.ConfFactory;
import com.unidt.helper.conf.IConf;
import com.unidt.helper.impl.DBFactory;
import com.unidt.helper.impl.FraMongoDB;
import com.unidt.helper.interf.IFraDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class FraDBTest {

    private IFraDB<Bson, FindIterable<Document>> mongo = new FraMongoDB();
    private FraMongoDB<Bson, FindIterable<Document>> raw_mongo = new FraMongoDB<>();

    public FraDBTest() throws Exception {
        IConf conf = ConfFactory.createPropertyConfig();
        String user = conf.getString("username");
        String pwd = conf.getString("password");
        String host = conf.getString("host");
        int port = Integer.parseInt(conf.getString("port"));
        mongo.connect(host, port,user, pwd);
        mongo.setDbname("dev");
        mongo.setCollection("dev");
        raw_mongo.setDbname("dev");
        raw_mongo.setCollection("dev");
        raw_mongo.connect(host,port,user, pwd);
    }

    public void testFind() throws Exception {
        Document filter = new Document("name","yangxiaodong");
        FindIterable<Document> result = mongo.find();

        MongoCursor<Document> cursor = result.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void testInsert() throws Exception {
        mongo.insertOne(new Document("name","1"));
        List<Bson> docs = new ArrayList<>();
        for( int i = 0 ; i < 10; i++) {
            docs.add(new Document("name", String.valueOf(i+2)));
        }
        mongo.insertMany(docs);
    }

    public void testDelete() throws Exception {
        mongo.delete(new Document("name", new Document("$ne","1")));
    }

    public void testUpdate() throws Exception {
        mongo.update(eq("name","xiaodong"), combine(set("name","xiaodong.yang"), set("age",30)));
    }
    public static  void main( String[] args) throws Exception {
        IFraDB db_dev = DBFactory.createMongoDB();

        db_dev.collection("t_dict").insertMany(Arrays.asList(
                new Document("key","1").append("value","未删除").append("parent","company_collection.delete"),
                new Document("key","0").append("value","已删除").append("parent","company_collection.delete"))
        );
    }
}
