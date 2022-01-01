package com.bohu.controller;


import com.bohu.pojo.Cordits;
import com.bohu.service.CorditsService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/cordits")
class CorditsController {

    @Resource
    private CorditsService corditsService;


    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/6/13 9:09 下午
     * @Param: [user]
     * @return:
     **/
    // 权限认证：当前会话必须具有指定权限才能通过 user-get
   // @SaCheckPermission("user-add")
    @PostMapping(value = "createCordite")
    public void createCordite(@RequestBody Cordits cordits) {
        corditsService.createCordite(cordits);
    }


}
