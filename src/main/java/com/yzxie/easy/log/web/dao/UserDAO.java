package com.yzxie.easy.log.web.dao;

import com.yzxie.easy.log.web.dao.entity.User;

/**
 * @author xieyizun
 * @date 7/11/2018 11:39
 * @description:
 */
public interface UserDAO {
    User selectById(Integer id);
    User selectByName(String name);
    User selectByEmail(String name);

    int insert(User user);
    int update(User user);
    int deleteById(Integer id);
}
