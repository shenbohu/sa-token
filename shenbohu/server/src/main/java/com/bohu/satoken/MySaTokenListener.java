package com.bohu.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName MySaTokenListener
 * @Author shenbohu
 * @Date 2021/6/182:02 下午
 * @Version 1.0  自定义侦听器的实现
 **/
@Component
public class MySaTokenListener implements SaTokenListener {
    private static final Log log = LogFactory.getLog(MySaTokenListener.class);
    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        log.info("用户"+loginId+"登录");
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        log.info("用户"+loginId+"注销");
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doLogoutByLoginId(String loginType, Object loginId, String tokenValue, String device) {
        log.info("用户"+loginId+"被踢下线");
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue, String device) {
        log.info("用户"+loginId+"被顶下线");
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {
        log.info("用户"+loginId+"被封禁");
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId) {
        log.info("用户"+loginId+"被解封");
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
        // ...
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
        // ...
    }

}


