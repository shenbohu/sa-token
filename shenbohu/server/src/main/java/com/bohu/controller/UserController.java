package com.bohu.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.util.SaTokenConsts;
import com.bohu.entity.PageResult;
import com.bohu.entity.Result;
import com.bohu.pojo.User;
import com.bohu.service.UserService;
import com.bohu.vo.UserVO;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
class UserController {

private static final Log log = LogFactory.getLog(UserController.class);
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password) {
        return userService.userLogn(username, password);

    }


    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/5/19 3:43 下午
     * @Param: [pageNum, pageSize]
     * @return:
     **/

    @GetMapping(value = "users/{pageNum}/{pageSize}")
    @SaCheckLogin
    public PageResult findAll(@PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize) {
        return userService.findAll(pageNum, pageSize);
    }

    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/5/19 5:49 下午
     * @Param: [id]
     * @return:
     **/
    // 角色认证：当前会话必须具有指定角色标识才能通过
    @GetMapping(value = "userbyid/{id}")
    @SaCheckLogin
    public Result getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }


    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/6/13 9:09 下午
     * @Param: [user]
     * @return: 用户注册
     **/
    // 权限认证：当前会话必须具有指定权限才能通过 user-get
    @SaCheckPermission("user-add")
    @PostMapping(value = "createUser")
    Result createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/6/13 9:55 下午
     * @Param: [userVO]
     * @return: 激活用户的账号
     **/
    @PutMapping(value = "updateUser")
    Result updateUser(@RequestBody UserVO userVO) {
        return userService.updateUser(userVO);
    }

    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/6/13 10:05 下午
     * @Param: [code] 给用户发送验证码
     * @return:
     **/
    @GetMapping(value = "getcode/{type}/{phone}")
    Result getcode(@PathVariable String type, @PathVariable String phone) {
        return userService.getcode(type, phone);
    }
}
