package com.bohu.dao.Appstore;


import com.bohu.pojo.User;
import com.bohu.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Author shenbohu
 * @Date 2021/5/1910:30 上午
 * @Version 1.0
 **/
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectall();

    UserVO  selectByUsernameAdnPassword(@Param("username") String username, @Param("password") String password);
}
