package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName NameController
 * @Author shenbohu
 * @Date 2021/8/55:43 下午
 * @Version 1.0
 **/

@RestController
public class NameController {
    @RequestMapping("/test")
    public String getName() {
        return "Hello Nacos!";
    }
}
