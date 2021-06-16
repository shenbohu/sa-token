package com.bohu.dao;

import com.bohu.pojo.Userrole;
import com.bohu.pojo.UserroleKey;

public interface UserroleMapper {
    int deleteByPrimaryKey(UserroleKey key);

    int insert(Userrole record);

    int insertSelective(Userrole record);

    Userrole selectByPrimaryKey(UserroleKey key);

    int updateByPrimaryKeySelective(Userrole record);

    int updateByPrimaryKey(Userrole record);
}