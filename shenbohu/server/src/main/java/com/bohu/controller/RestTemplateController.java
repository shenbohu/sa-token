package com.bohu.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.bohu.entity.PageResult;
import com.bohu.service.HJ212Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @ClassName FileUploadController
 * @Author shenbohu
 * @Date 2021/6/201:07 上午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/hj212")
@CrossOrigin // 解决跨域
public class RestTemplateController {
    @Resource
    private HJ212Service hj212Service;

    @GetMapping(value = "gethj212")
    @SaCheckLogin
    public PageResult gethj212() {
        return hj212Service.gethj212();
    }


}
