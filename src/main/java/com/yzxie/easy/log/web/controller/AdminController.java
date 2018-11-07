package com.yzxie.easy.log.web.controller;

import com.yzxie.easy.log.web.dao.entity.Role;
import com.yzxie.easy.log.web.dao.entity.UserRole;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xieyizun
 * @date 7/11/2018 18:46
 * @description:
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/addRole")
    public ResData addRole(@RequestBody Role role) {
        ResData resData = new ResData();
        if (role != null) {
            int roleId = userService.addRole(role);
            resData.setData(roleId);
        }
        return resData;
    }

    @PostMapping("/addUserRole")
    public ResData addUserRole(@RequestBody UserRole userRole) {
        ResData resData = new ResData();
        if (userRole != null) {
            int userRoleId = userService.addUserRole(userRole);
            resData.setData(userRoleId);
        }
        return resData;
    }
}
