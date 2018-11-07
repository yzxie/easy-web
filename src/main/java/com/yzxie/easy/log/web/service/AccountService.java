package com.yzxie.easy.log.web.service;

import com.yzxie.easy.log.web.dao.UserDAO;
import com.yzxie.easy.log.web.dao.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xieyizun
 * @date 7/11/2018 18:30
 * @description:
 */
@Service
public class AccountService {
    @Autowired
    private UserDAO userDAO;

    public boolean registerUser(User user) {
        // 将用户名作为盐值，与realm加盐方式一致
        ByteSource salt = ByteSource.Util.bytes(user.getName());
        // 生成加密密码
        String newPassword = new SimpleHash("MD5", user.getPassword(), salt, 1024).toHex();
        user.setPassword(newPassword);

        // 查看数据库是否存在该账户
        User oldUser = userDAO.selectByName(user.getName());
        if (oldUser == null) {
            userDAO.insert(user);
            return true;
        }

        return false;
    }
}
