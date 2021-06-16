package com.bohu.vo;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.bohu.pojo.Role;
import com.bohu.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * @ClassName UserVO
 * @Author shenbohu
 * @Date 2021/6/139:48 下午
 * @Version 1.0
 **/
@Data
public class UserVO extends User {
    private String code;

    private SaSession session;

    private String tokenValueByLoginId;  //token令牌值

    private List<Role> roles; //用户的角色

    private List<RightVO> rightVOS; //用户的权限

    private SaTokenInfo tokenInfo; //token详细参数


}
