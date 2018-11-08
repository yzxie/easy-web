package com.yzxie.easy.log.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzxie.easy.log.web.dao.entity.User;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xieyizun
 * @date 7/11/2018 18:06
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResData profile(@PathVariable String userName) {
        ResData resData = new ResData();
        User user = userService.selectUserByName(userName);
        if (user != null) {
            JSONObject userInfo = new JSONObject();
            userInfo.put("userInfo", user);
            resData.setData(userInfo);
        }
        return resData;
    }
}
