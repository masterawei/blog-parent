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

    /**
     * 检查token
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
