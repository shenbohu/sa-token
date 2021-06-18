import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;

/**
 * @ClassName satoke
 * @Author shenbohu
 * @Date 2021/6/145:19 下午
 * @Version 1.0
 **/
public class satoke {
    public static void main(String[] args) {
//        StpUtil.setLoginId("loginId");// 在用户账号密码验证成功后，直接调用以下API进行登录授权
//        // StpUtil.setLoginId(loginId, "PC");// 指定设备标识登录
//
//
//        StpUtil.checkLogin();// 如果当前会话未登录，这句代码会抛出 `NotLoginException`异常
//        Object loginId = StpUtil.getLoginId();// 获取当前会话登录的账号id
//        // boolean isLogin = StpUtil.isLogin();// 获取当前会话是否已经登录, 返回true或false
//        SaSession session = StpUtil.getSession();// 获取当前账号id的Session
//        // SaSession sessionByLoginId = StpUtil.getSessionByLoginId(loginId);// 获取账号id为10001的Session
//        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(loginId);// 获取账号id为10001的token令牌值
//        // StpUtil.switchTo(10044);// 将当前会话身份临时切换为其它账号
//        // boolean hasRole = StpUtil.hasRole("super-admin");// 查询当前账号是否含有指定角色标识, 返回true或false
//        // boolean hasPermission = StpUtil.hasPermission("user:add");// 查询当前账号是否含有指定权限, 返回true或false
//
//
//        // boolean hasPermission = StpUtil.hasPermission("user:update");// 当前账号是否含有指定权限, 返回true或false
//        StpUtil.checkPermission("user:update");// 当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException
//        // StpUtil.checkPermissionAnd("user:update", "user:delete");// 当前账号是否含有指定权限 [指定多个，必须全部验证通过]
//        // StpUtil.checkPermissionOr("user:update", "user:delete");// 当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]
//
//        // boolean hasRole = StpUtil.hasRole("user");// 当前账号是否含有指定角色标识, 返回true或false
//        StpUtil.checkRole("user");// 当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException
//        // StpUtil.checkRoleAnd("user", "admin");// 当前账号是否含有指定角色标识 [指定多个，必须全部验证通过]
//        // StpUtil.checkRoleOr("user", "admin");// 当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可]
//
//        StpUtil.logoutByLoginId(loginId);// 将某个账号踢下线 (待到对方再次访问系统时会抛出NotLoginException异常)
//        // StpUtil.logout();// 当前会话注销登录
//        // StpUtil.logoutByLoginId(loginId, "PC");// 指定设备标识进行强制注销 (不同端不受影响)

        // 生成一对公钥和私钥，其中Map对象 (private=私钥, public=公钥)
        try {
            System.out.println(SaSecureUtil.rsaGenerateKeyPair());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
