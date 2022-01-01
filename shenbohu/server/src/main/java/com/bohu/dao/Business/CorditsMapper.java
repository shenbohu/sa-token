package com.bohu.dao.Business;

import com.bohu.pojo.Cordits;

public interface CorditsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Cordits record);

    int insertSelective(Cordits record);

    Cordits selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Cordits record);

    int updateByPrimaryKey(Cordits record);
}