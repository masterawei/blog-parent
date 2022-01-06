package com.mszlu.blog.controller;

import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author by away
 * @date 2022/1/6 14:36
 */
@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
