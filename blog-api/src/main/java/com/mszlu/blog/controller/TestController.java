package com.mszlu.blog.controller;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by away
 * @date 2022/1/7 13:34
 */
@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping
    public Result test() {
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }


}
