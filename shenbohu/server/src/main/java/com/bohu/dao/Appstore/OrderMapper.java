package com.bohu.dao.Appstore;

import com.bohu.pojo.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}