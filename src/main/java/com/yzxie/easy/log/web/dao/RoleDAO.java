package com.yzxie.easy.log.web.dao;

import com.yzxie.easy.log.web.dao.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * @author xieyizun
 * @date 7/11/2018 18:08
 * @description:
 */
@Repository
public interface RoleDAO {
    int addRole(String name);
}
