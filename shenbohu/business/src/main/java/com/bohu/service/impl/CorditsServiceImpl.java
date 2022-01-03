package com.bohu.service.impl;


import com.bohu.dao.Business.CorditsMapper;
import com.bohu.pojo.Cordits;
import com.bohu.service.CorditsService;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.UUID;


/**
 * @ClassName CorditsServiceImpl
 * @Author shenbohu
 * @Date 2021/5/1910:20 上午
 * @Version 1.0
 **/
@Service
@Transactional
@Configuration
@Data
public class CorditsServiceImpl implements CorditsService {
    @Resource
    private CorditsMapper corditsMapper;

    @Override
    public void createCordite(Cordits cordits) {
        cordits.setId(UUID.randomUUID().toString());
//      if (cordits.getUserid().length()>6) {
//           throw new RuntimeException("上行出错");
//      }
        try {
          Thread.sleep(20000);
       } catch (InterruptedException e) {
            e.printStackTrace();
       }

        corditsMapper.insert(cordits);
    }
}
