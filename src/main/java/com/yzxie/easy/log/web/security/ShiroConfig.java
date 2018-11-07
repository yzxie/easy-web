package com.yzxie.easy.log.web.security;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xieyizun
 * @date 7/11/2018 15:56
 * @description:
 */
@Configuration
public class ShiroConfig {
    /**
     * 拦截规则配置
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器，匹配原则为最上面的最优先匹配
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的接口
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/hello", "anon");
        // 配置退出过滤器，其中的具体退出代码shiro已经实现
        filterChainDefinitionMap.put("/doLogout", "logout");
        shiroFilterFactoryBean.setLoginUrl("/hello");
        // 角色控制
        filterChainDefinitionMap.put("/user/**", "roles[user]");
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        // 剩余请求需要身份认证
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 认证、授权实际工作类，自定义认证方式的实现
     * @param matcher
     * @return
     */
    @Bean(name = "myShiroRealm")
    public ShiroRealm myShiroRealm(HashedCredentialsMatcher matcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        // 明文密码与密文密码匹配
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }

    /**
     * 统一权限管理器，对外提供认证服务
     * @param matcher
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm(matcher));
        return securityManager;
    }

    /**
     * 明文密码和密文密码匹配管理器
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用MD5方式加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}
