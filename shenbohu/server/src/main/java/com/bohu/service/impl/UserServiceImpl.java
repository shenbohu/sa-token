package com.bohu.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
//import com.baomidou.dynamic.datasource.annotation.DS;
import com.bohu.dao.Appstore.OrderMapper;
import com.bohu.dao.Appstore.RightMapper;
import com.bohu.dao.Appstore.RoleMapper;
import com.bohu.dao.Appstore.UserMapper;
import com.bohu.dao.Business.CorditsMapper;
import com.bohu.entity.PageResult;
import com.bohu.entity.Result;
import com.bohu.entity.YmlConfig;
import com.bohu.feign.corditsfeign;
import com.bohu.feign.pojo.cordits;
import com.bohu.pojo.Cordits;
import com.bohu.pojo.Order;
import com.bohu.pojo.Role;
import com.bohu.pojo.User;
import com.bohu.service.UserService;
import com.bohu.utils.SMSUtils;
import com.bohu.utils.StatusCode;
import com.bohu.utils.ValidateCodeUtils;
import com.bohu.vo.RightVO;
import com.bohu.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.SneakyThrows;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName UserServiceImpl
 * @Author shenbohu
 * @Date 2021/5/1910:20 上午
 * @Version 1.0
 **/
@Service
//@Configuration
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

    @Autowired
    private corditsfeign corditsfeign;

    @Resource
    private CorditsMapper corditsMapper;

    @Autowired
    private OrderMapper orderMapper;




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
    //@GlobalTransactional
   //@DS("gits_sharding")
    public Result createUser(User user) {
        try {
            if (StringUtils.isEmpty(user.getPassword())) {
                return new Result(false, StatusCode.ERROR, "");
            }
            user.setPassword(SaSecureUtil.md5(user.getPassword()));
            user.setCreated(new Date());
            user.setStatus("0");
            userMapper.insertSelective(user);
            Cordits cordits = new Cordits();
            cordits.setUserid(user.getUsername());
            cordits.setNum("30");
            cordits.setId(UUID.randomUUID().toString());
            //corditsfeign.createcrodits(cordits);
            corditsMapper.insert(cordits);

            for (int i = 0; i < 10; i++) {
                Order order = new Order();
                order.setType("111");
                order.setOid(Integer.toString(i));
                orderMapper.insert(order);
            }


        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
                        , ymlConfig.getSmsutils().get("SignName")
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


    @SneakyThrows
    @Override
    public Result getcodeh() {
        String h212 = "##0136ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n";

        return new Result(true, StatusCode.OK, h212);
    }
}
