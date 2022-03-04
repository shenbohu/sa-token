package com.bohu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bohu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import com.bohu.entity.PageResult;
import com.bohu.entity.YmlConfig;

import com.bohu.service.HJ212Service;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


/**
 * @ClassName UserServiceImpl
 * @Author shenbohu
 * @Date 2021/5/1910:20 上午
 * @Version 1.0
 **/
@Service
@Transactional
@Configuration
@Data
public class HJ212ServiceImpl implements HJ212Service {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private YmlConfig ymlConfig;

    @Override
    public PageResult gethj212() {
        System.out.println("ccccccccc");
        String json = doGetWith1(String.valueOf(11));
        System.out.println(json);
        return null;
    }

    /**
     * 以get方式请求第三方http接口 getForEntity
     * @param url
     * @return
     */
    public String doGetWith1(String url){
        url = "https://cover.yixinda.com/cover-device?companyId=111";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String  body = responseEntity.getBody();
        return body;
    }

    /**
     * 以get方式请求第三方http接口 getForObject
     * 返回值返回的是响应体，省去了我们再去getBody()
     * @param url
     * @return
     */
    public User doGetWith2(String url){
        User user  = restTemplate.getForObject(url, User.class);
        return user;
    }

    /**
     * 以post方式请求第三方http接口 postForEntity
     * @param url
     * @return
     */
    public String doPostWith1(String url){
        User user = new User();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, user, String.class);
        String body = responseEntity.getBody();
        return body;
    }

    /**
     * 以post方式请求第三方http接口 postForEntity
     * @param url
     * @return
     */
    public String doPostWith2(String url){
        User user = new User();
        String body = restTemplate.postForObject(url, user, String.class);
        return body;
    }

    /**
     * exchange
     * @return
     */
    public String doExchange(String url, Integer age, String name){
        //header参数
        HttpHeaders headers = new HttpHeaders();
        String token = "asdfaf2322";
        headers.add("authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //放入body中的json参数
        JSONObject obj = new JSONObject();
        obj.put("age", age);
        obj.put("name", name);

        //组装
        HttpEntity<JSONObject> request = new HttpEntity<>(obj, headers);
        String s = request.toString();
        return s;
    }


}
