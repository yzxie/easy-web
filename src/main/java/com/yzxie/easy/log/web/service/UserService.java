package com.yzxie.easy.log.web.service;

import com.yzxie.easy.log.web.dao.UserDAO;
import com.yzxie.easy.log.web.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean addUser(User user) {
        try {
           int count = userDAO.insert(user);
           return count==1;
        } catch (Exception e) {
            LOG.error("UserService addUser exception {}", e, e.getMessage());
        }
        return false;
    }
}
