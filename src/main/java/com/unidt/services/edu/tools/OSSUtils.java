package com.unidt.services.edu.tools;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by admin on 2018/1/26.
 */
public class OSSUtils {

    public static Logger log = LoggerFactory.getLogger(OSSUtils.class);
    public static Properties properties = new Properties();
    public COSClient    client = null;

    public OSSUtils(){
        InputStream io = OSSUtils.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(io);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connectOSS(){
        String secretID = properties.getProperty("oss.secretID");
        String secretKey = properties.getProperty("oss.secretKey");
        COSCredentials cred = new BasicCOSCredentials(secretID, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        client = new COSClient(cred, clientConfig);
        return true;
    }
    public boolean close(){
        client.shutdown();
        return true;
    }

    /**
     *  文件对象上传
     * @param bucket
     * @param src
     * @param dest
     */
    public void upload(String bucket, File src, String dest){
       /* File localfile = new File(src);*/
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, dest, src);
        client.putObject(putObjectRequest);
    }


    public void uploadFile(String bucket, String src, String dest){
        File localfile = new File(src);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, dest, localfile);
        client.putObject(putObjectRequest);
    }
    /**
     *  文件对象下载
     * @param bucket
     * @param src
     * @return
     */
    public Object download(String bucket, String src){
        File localfile = new File(src);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, src);
            ObjectMetadata downObjectMeta = client.getObject(getObjectRequest, localfile);
        return downObjectMeta;
    }

    /**
     * 删除对象存储文件
     * @param bucket
     * @param src
     */
    public void deleteFile(String bucket, String src){
        File localfile = new File(src);
        client.deleteObject(bucket, src);
    }



    public static void main(String[] args) throws IOException {
        OSSUtils os = new OSSUtils();
        os.connectOSS();
       // os.upload("shujia-1255423687", "D:\\测试文件.txt", "/k12resources/测试文件.txt");
        os.close();
        log.info("OK");
    }
}
