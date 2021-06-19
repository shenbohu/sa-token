package com.bohu.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.bohu.dao.RightMapper;
import com.bohu.dao.RoleMapper;
import com.bohu.dao.UserMapper;
import com.bohu.entity.PageResult;
import com.bohu.entity.Result;
import com.bohu.entity.YmlConfig;
import com.bohu.pojo.Role;
import com.bohu.pojo.User;
import com.bohu.service.UserService;
import com.bohu.utils.MD5Utils;
import com.bohu.utils.SMSUtils;
import com.bohu.utils.StatusCode;
import com.bohu.utils.ValidateCodeUtils;
import com.bohu.vo.RightVO;
import com.bohu.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName UserServiceImpl
 * @Author shenbohu
 * @Date 2021/5/1910:20 上午
 * @Version 1.0
 **/
@Service
@Transactional
@Configuration
@Data
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RightMapper rightMapper;

    @Autowired
    private YmlConfig ymlConfig;



    @Override
    public PageResult findAll(String pageNum, String pageSize) {
        Page page = PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        List<User> userList = userMapper.selectall();
        return new PageResult(page.getTotal(), userList);
    }

    @Override
    public Result getUserById(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return new Result(true, StatusCode.OK, user);
    }

    @Override
    public Result createUser(User user) {
        try {
            if (StringUtils.isEmpty(user.getPassword())) {
                return new Result(false, StatusCode.ERROR, "");
            }
            user.setPassword(SaSecureUtil.md5(user.getPassword()));
            user.setCreated(new Date());
            user.setStatus("0");
            userMapper.insertSelective(user);
        } catch (Exception e) {
            return new Result(false, StatusCode.USERNAME);
        }
        return new Result(true, StatusCode.OK, user);
    }

    @Override
    public Result updateUser(UserVO userVO) {
        if (StringUtils.isEmpty(userVO.getCode())) {
            return new Result(false, StatusCode.ERROR, "请输入验证码");
        }
        try {
            String o = (String) redisTemplate.opsForValue().get(userVO.getUsername() + "code");
            if (Objects.equals(o, userVO.getCode())) {
                userVO.setStatus("1");
            }
            userMapper.updateByPrimaryKey(userVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, StatusCode.OK, "cg");
    }

    @Override
    public Result getcode(String type, String phone) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(phone)) {
            return new Result(false, StatusCode.ERROR, "参数不全");
        }
        Integer integer = ValidateCodeUtils.generateValidateCode(6);
        try {
            if (Objects.equals("JH", type)) {
                boolean b = SMSUtils.sendShortMessage(ymlConfig.getSmsutils()
                        .get("templateCode.validate_codez"),
                        phone, integer.toString()
                        ,ymlConfig.getSmsutils().get("SignName")
                );
                if (b) {
                    redisTemplate.opsForValue().set(phone + "JH", integer.toString(), 5, TimeUnit.MINUTES);
                } else {
                    return new Result(false, StatusCode.ERROR, "发送失败");
                }

            } else if (Objects.equals("DL", type)) {
                redisTemplate.opsForValue().set(phone + "DL", integer.toString(), 5, TimeUnit.MINUTES);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Result userLogn(String username, String password) {
        //校验参数
        if (org.apache.commons.lang.StringUtils.isEmpty(username)) {
            throw new RuntimeException("请输入用户名");
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(password)) {
            throw new RuntimeException("请输入密码");
        }
//        password = MD5Utils.md5(password);
        password = SaSecureUtil.md5(password);
        UserVO userVO = userMapper.selectByUsernameAdnPassword(username, password);
        if (userVO == null) {
            return new Result(false, StatusCode.ERROR, "userVO");
        }
        List<Role> roles = roleMapper.selectByUserRole(username);
        List<RightVO> rightVOS = new ArrayList<>();
        userVO.setRoles(roles);
        if (roles.size() != 0) {
            List<String> roleids = new ArrayList<>();
            for (Role role : roles) {
                roleids.add(role.getRoleid());
            }
            rightVOS = rightMapper.selectByIds(roleids);
            userVO.setRightVOS(rightVOS);
        }
        StpUtil.login(username);// 在用户账号密码验证成功后，直接调用以下API进行登录授权
        SaSession session = StpUtil.getSession();// 获取当前账号id的Session
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(username);// 获取账号id为username的token令牌值
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();//返回当前会话的token详细参数
        userVO.setTokenInfo(tokenInfo);
        userVO.setSession(session);
        userVO.setTokenValueByLoginId(tokenValueByLoginId);
        //存储
        if (roles.size() != 0) {
            redisTemplate.opsForList().rightPushAll(username + "role", roles);
        } else {
            redisTemplate.opsForList().rightPushAll(username + "rolenull", "null");
        }
        if (rightVOS.size() != 0) {
            redisTemplate.opsForList().rightPushAll(username + "right", rightVOS);
        } else {
            redisTemplate.opsForList().rightPushAll(username + "rightnull", "null");
        }

        return new Result(true, StatusCode.OK, userVO);
    }
}
