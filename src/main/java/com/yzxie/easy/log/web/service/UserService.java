package com.yzxie.easy.log.web.service;

import com.yzxie.easy.log.web.dao.RoleDAO;
import com.yzxie.easy.log.web.dao.UserDAO;
import com.yzxie.easy.log.web.dao.UserRoleDAO;
import com.yzxie.easy.log.web.dao.entity.Role;
import com.yzxie.easy.log.web.dao.entity.User;
import com.yzxie.easy.log.web.dao.entity.UserRole;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author xieyizun
 * @date 7/11/2018 12:52
 * @description:
 */
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private RoleDAO roleDAO;

    /**
     * 添加用户
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        try {
           int count = userDAO.insert(user);
           return count==1;
        } catch (Exception e) {
            LOG.error("UserService addUser exception {}", e, e.getMessage());
        }
        return false;
    }

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    public User selectUserByName(String name) {
        return userDAO.selectByName(name);
    }

    /**
     * 获取用户的角色
     * @param userName
     * @return
     */
    public Set<String> getUserRoles(String userName) {
        return userRoleDAO.getRolesByUserName(userName);
    }

    /**
     * 添加用户角色
     * @param userRole
     * @return
     */
    public int addUserRole(UserRole userRole) {
        return userRoleDAO.addUserRole(userRole.getUserId(), userRole.getRoleId());
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    public int addRole(Role role) {
        return roleDAO.addRole(role.getName());
    }
}
