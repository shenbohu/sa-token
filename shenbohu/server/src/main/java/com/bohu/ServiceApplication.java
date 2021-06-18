package com.bohu;


import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端
@MapperScan("com.bohu.dao")
@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
public class ServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
        System.out.println("启动成功：sa-token配置如下：" + SaManager.getConfig());

//        System.out.println("\n------ 启动成功 ------");
//        System.out.println("name: " + SaQuickManager.getConfig().getName());
//        System.out.println("pwd:  " + SaQuickManager.getConfig().getPwd());

    }

}
