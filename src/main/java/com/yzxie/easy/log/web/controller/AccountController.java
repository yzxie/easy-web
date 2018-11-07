package com.yzxie.easy.log.web.controller;

import com.yzxie.easy.log.web.dao.entity.User;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.AccountService;
import com.yzxie.easy.log.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xieyizun
 * @date 7/11/2018 12:51
 * @description:
 */
@RestController
public class AccountController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResData register(@RequestBody User user) {
        long start = System.currentTimeMillis();
        ResData res = new ResData();

        if (user != null) {
            boolean succeed = accountService.registerUser(user);
            if (succeed) {
                res.setData("register successfully.");
            } else {
                res.setData("register failure");
            }
        }

        LOG.info("register: {} ms", System.currentTimeMillis() - start);
        return res;
    }

    @PostMapping("/login")
    public ResData login(@RequestBody User user) {
        ResData res = new ResData();
        // 认证个体
        Subject currentUser = SecurityUtils.getSubject();
        // 将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());

        try {
            currentUser.login(token);
            if (currentUser.isAuthenticated()) {
                res.setData("login succeed.");
            } else {
                res.setData("login failure");
            }
        } catch (AuthenticationException e) {
            LOG.error("login failure {}", e, e.getMessage());
        }

        return res;
    }

    @GetMapping("/doLogout")
    public ResData logout() {
        return new ResData("logout succeed");
    }

}
