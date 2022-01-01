package com.bohu.feign;

import com.bohu.feign.pojo.cordits;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName corditsfeign
 * @Author shenbohu
 * @Date 2021/12/285:18 PM
 * @Version 1.0
 **/

@FeignClient(name = "business")
@RequestMapping(value = "cordits")
public interface corditsfeign {

    @PostMapping(value = "/createCordite" ,produces = "application/json; charset=UTF-8")
    void createcrodits(@RequestBody cordits crodits) ;


}
