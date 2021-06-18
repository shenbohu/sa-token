package com.bohu;

import org.assertj.core.util.DateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Date;

@SpringBootApplication
@EnableEurekaServer //开启Eureka服务
public class EurekaApplication  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        System.out.println("===========程 序 启 动===========");
        // 当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        // DateUtil 第三方jar包中的类
        Date now = DateUtil.now();
        System.out.println("当前时间：" + now);
        SpringApplication.run(EurekaApplication.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(
//            SpringApplicationBuilder builder) {
//        return builder.sources(this.getClass());
//    }


}
