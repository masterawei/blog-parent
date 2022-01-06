package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;

/**
 * @author by away
 * @date 2022/1/5 14:19
 */
public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);
}
