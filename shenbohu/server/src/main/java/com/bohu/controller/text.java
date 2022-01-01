package com.bohu.controller;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * @ClassName text
 * @Author shenbohu
 * @Date 2021/6/198:58 下午
 * @Version 1.0
 **/
public class text {
    public static void main(String[] args) throws FileNotFoundException {
//        File file = ResourceUtils.getFile("classpath:document/weixin.jpeg");
//        String absolutePath = file.getAbsolutePath();
//        System.out.println(absolutePath);

        for (int i = 0; i < 10000; i++) {
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }


    }
}
