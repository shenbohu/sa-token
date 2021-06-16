package com.bohu.dao;

import com.bohu.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(String roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByUserRole(String username);
}