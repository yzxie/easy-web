package com.yzxie.easy.log.web.security;


import com.yzxie.easy.log.web.dao.entity.User;
import com.yzxie.easy.log.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 自定义认证和授权实现
 * @author xieyizun
 * @date 7/11/2018 16:08
 * @description:
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 认证
     * @param authenticationToken 需要认证的token，通常在登陆接口构建
     * @return 可以使用SimpleAuthenticationInfo实现类，封装正确的用户名和密码。认证失败，则返回null
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 将token封装成UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        // 获取用户名
        String userName = usernamePasswordToken.getUsername();
        User user = userService.selectUserByName(userName);
        if (user != null) {
            Object principal = user.getName();
            Object credentials = user.getPassword();

            // 获取盐值，即用户名
            ByteSource salt = ByteSource.Util.bytes(userName);
            // 将用户名，密码，盐值，realmName实例化到SimpleAuthenticationInfo交给Shiro来管理
            String realName = this.getName();
            return new SimpleAuthenticationInfo(principal, credentials, salt, realName);
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        // 获取当前用户持有的角色
        Set<String> roles = userService.getUserRoles(userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (roles != null) {
            info.setRoles(roles);
        }
        return info;
    }
}
