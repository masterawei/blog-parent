package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;

/**
 * @author by away
 * @date 2022/1/5 14:19
 */
public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查找用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);
}
