package com.bohu.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.bohu.entity.PageResult;
import com.bohu.service.CourseService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/course")
class CourseController {

    private static final Log log = LogFactory.getLog(CourseController.class);
    @Resource
    private CourseService courseService;


    @GetMapping(value = "getallcourse/{pageNum}/{pageSize}")
    //@SaCheckLogin
    public PageResult getallcourse(@PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize) {
        return courseService.getallcourse(pageNum, pageSize);
    }


//    @GetMapping(value = "userbyid/{id}")
//    @SaCheckLogin
//    public Result getUserById(@PathVariable("id") String id) {
//        return userService.getUserById(id);
//    }


}
