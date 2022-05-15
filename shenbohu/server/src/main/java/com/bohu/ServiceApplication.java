package com.bohu;


import cn.dev33.satoken.SaManager;
//import cn.dev33.satoken.quick.SaQuickManager;
//import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;



@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication(exclude = SpringBootConfiguration.class)
//@EnableEurekaClient //开启Eureka客户端
//@MapperScan("com.bohu.dao")
@EnableFeignClients(basePackages = "com.bohu.feign")
@EnableDiscoveryClient
public class ServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
        System.out.println("\n------ 启动成功 ------");
//        System.out.println("name: " + SaQuickManager.getConfig().getName());
//        System.out.println("pwd:  " + SaQuickManager.getConfig().getPwd());
        System.out.println("sa-token配置如下：" + SaManager.getConfig());

    }

}
