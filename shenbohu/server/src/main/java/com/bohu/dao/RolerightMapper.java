package com.bohu.dao;

import com.bohu.pojo.Roleright;
import com.bohu.pojo.RolerightKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolerightMapper {
    int deleteByPrimaryKey(RolerightKey key);

    int insert(Roleright record);

    int insertSelective(Roleright record);

    Roleright selectByPrimaryKey(RolerightKey key);

    int updateByPrimaryKeySelective(Roleright record);

    int updateByPrimaryKey(Roleright record);
}