package com.bohu.service;

import com.bohu.pojo.User;
import com.bohu.entity.PageResult;
import com.bohu.entity.Result;
import com.bohu.vo.UserVO;

public interface UserService {


    PageResult findAll(String pageNum, String pageSize);

    Result getUserById(String id);

    Result createUser(User user);

    Result updateUser(UserVO userVO);

    Result getcode(String type, String phone);

    Result userLogn(String username, String password);


    Result getcodeh();

}
