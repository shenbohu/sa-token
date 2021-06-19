package com.bohu.entity;

/**
 * @ClassName YmlConfig
 * @Author shenbohu
 * @Date 2021/6/202:10 上午
 * @Version 1.0
 **/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载yaml配置文件的方法
 * Created by sun on 2017-1-15.
 * spring-boot更新到1.5.2版本后locations属性无法使用
 * @PropertySource注解只可以加载proprties文件,无法加载yaml文件
 * 故现在把数据放到application.yml文件中,spring-boot启动时会加载
 */
@Component
//@ConfigurationProperties(locations = {"classpath:config/myProps.yml"},prefix = "myProps")
@ConfigurationProperties(prefix = "aliyun")
@Data
public class YmlConfig {


    private Map<String, String> smsutils = new HashMap<>(); //接收prop1里面的属性值





}