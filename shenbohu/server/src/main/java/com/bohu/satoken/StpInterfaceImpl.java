package com.bohu.satoken;


import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.bohu.dao.RoleMapper;
import com.bohu.pojo.Role;
import com.bohu.vo.RightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName StpInterfaceImpl
 * @Author shenbohu 自定义权限验证接口扩展
 * @Date 2021/6/148:40 下午
 * @Version 1.0
 **/
@Component    // 保证此类被SpringBoot扫描，完成sa-token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {
    /**
     * 返回一个账号所拥有的权限码集合
     */

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        List<String> list = new ArrayList<>();
        Boolean aBoolean = redisTemplate.hasKey(o + "rightnull");
        if (aBoolean) {
            return list;
        }
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<RightVO> rightVOS = redisTemplate.opsForList().range(o + "right", 0, -1);
        if (rightVOS.size() == 0) {
            StpUtil.logoutByLoginId(o);           // 让账号为10001的会话注销登录（踢人下线）
        }
        rightVOS.forEach(rightVO -> {
            list.add(rightVO.getC());
            list.add(rightVO.getR());
            list.add(rightVO.getU());
            list.add(rightVO.getD());
        });
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> list = new ArrayList<>();
        Boolean aBoolean = redisTemplate.hasKey(o + "rolenull");
        if (aBoolean) {
            return list;
        }
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<Role> roles = redisTemplate.opsForList().range(o + "role", 0, -1);
        if (roles.size() == 0) {
            StpUtil.logoutByLoginId(o);           // 让账号为10001的会话注销登录（踢人下线）
        }
        roles.forEach(role -> {
            list.add(role.getLogo());
        });
        return list;
    }
}
