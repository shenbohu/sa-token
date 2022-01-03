package com.bohu;

import com.bohu.dao.Appstore.OrderMapper;
import com.bohu.pojo.Order;
import com.bohu.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName OrderTest
 * @Author shenbohu
 * @Date 2021/12/3111:58 AM
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})
public class OrderTest {

     @Autowired
     OrderMapper orderMapper;



    @Test
    public  void insertOrderTest() {
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setType("111");
            order.setOid(Integer.toString(i));
            TUser tUser = new TUser();
            tUser.setName("11111");


        }


    }



}
