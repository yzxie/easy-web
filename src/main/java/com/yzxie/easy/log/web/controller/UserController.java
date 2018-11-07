package com.yzxie.easy.log.web.controller;

import com.yzxie.easy.log.web.dao.entity.User;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xieyizun
 * @date 7/11/2018 12:51
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResData register(@RequestBody User user) {
        long start = System.currentTimeMillis();
        ResData res = new ResData();

        if (user != null) {
            boolean succeed = userService.addUser(user);
            if (succeed) {
                res.setData("register successfully.");
            } else {
                res.setData("register failure");
            }
        }

        LOG.info("register: {} ms", System.currentTimeMillis() - start);
        return res;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResData login(@RequestBody User user) {
        ResData res = new ResData();

        return res;
    }

}
