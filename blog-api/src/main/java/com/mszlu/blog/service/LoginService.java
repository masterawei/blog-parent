package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;

/**
 * @author by away
 * @date 2022/1/6 13:24
 */
public interface LoginService  {
    /**
     * 登录功能
     * @param loginParam
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);
}
