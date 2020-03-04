package com.shirodemo.shiro;

import com.shirodemo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;


public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        //给资源授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取当前登录账户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        //省略数据库查询
        User dbUser = user;

        //添加资源的授权字符串
//        info.addStringPermission("user:add");
        if (dbUser.getPerms() != null && !"".equals(dbUser.getPerms())) {
            info.addStringPermission(dbUser.getPerms());
        }

        return info;
    }


    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //编写shiro判断逻辑
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        //假定数据库查询出以下用户
        User user1 = new User("000000", "name", "123456", "user:add");
        User user2 = new User("000001", "name2", "123456", null);
        User user = null;
        if (username.equals(user1.getName())) {
            user = user1;
        }
        if (username.equals(user2.getName())) {
            user = user2;
        }


        //判断用户名
        if (user == null) {
            //用户名不存在
            return null; //shiro会抛出一个UnknowAccountException
        }

        //判断密码, 返回登录用户信息
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}

