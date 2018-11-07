package com.yzxie.easy.log.web.dao;

import com.yzxie.easy.log.web.dao.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author xieyizun
 * @date 7/11/2018 18:08
 * @description:
 */
@Repository
public interface UserRoleDAO {
    Set<String> getRolesByUserName(String userName);

    int addUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
