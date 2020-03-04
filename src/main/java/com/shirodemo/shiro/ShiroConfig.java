package com.shirodemo.shiro;
import	java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //关联安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * 添加Shiro内置过滤器
         * 可以实现权限相关的拦截
         * 常用过滤器:
         *  anon:无需认证
         *  authc:必须认证
         *  user:如果使用rememberMe的功能可以直接访问
         *  perms:必须得到该资源权限才可以访问
         *  role:必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String, String> ();

        //添加认证过滤器
        filterMap.put("/shiro/add", "authc");
        filterMap.put("/shiro/update", "authc");
        /*
        filterMap.put("/shrio/*", "atuhc");
         */

        //添加授权过滤器
        filterMap.put("/shiro/add", "perms[user:add]");

        //修改跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/shiro/tologin");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index.html");
//        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/shiro/403");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //关联realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }
}
