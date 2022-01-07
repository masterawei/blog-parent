package com.mszlu.blog.utils;

import com.mszlu.blog.dao.pojo.SysUser;

/**
 * @author by away
 * @date 2022/1/7 13:45
 */
public class UserThreadLocal {
    private UserThreadLocal(){}
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
