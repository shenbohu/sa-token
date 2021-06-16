package com.bohu;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端
@MapperScan("com.bohu.dao")
@EnableTransactionManagement
public class ServiceApplication {


    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(ServiceApplication.class, args);
//        System.out.println("启动成功：sa-token配置如下：" + satoken.getConfig());

    }

}
