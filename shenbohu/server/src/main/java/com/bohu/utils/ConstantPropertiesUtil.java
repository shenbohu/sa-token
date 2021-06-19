package com.bohu.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.keyid}")
    private String keyId;

    @Value("${aliyun.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    // 定义常量，为了能够使用
    public static String ENDPOINT;
    public static String KEYID;
    public static String KEYSECRET;
    public static String BUCKETNAME;
    /**
     * 服务器启动的时候，ConstantPropertiesUtil初始化，调用里面的afterPropertiesSet方法，读取配置文件的内容
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endPoint;
        KEYID = keyId;
        KEYSECRET = keySecret;
        BUCKETNAME = bucketName;
    }
}
