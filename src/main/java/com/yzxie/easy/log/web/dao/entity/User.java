package com.yzxie.easy.log.web.dao.entity;

import lombok.Data;

/**
 * @author xieyizun
 * @date 7/11/2018 11:49
 * @description:
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "User=["+name+","+email+"]";
    }
}
