package test;

import org.bson.Document;

public class DocumentTest {
    public static void main(String [] args) {
        Document doc = new Document();

        doc.append("token",1);
        System.out.println(doc.toJson());

        doc.put("token",2);
        System.out.println(doc.toJson());
    }
}
