package com.mszlu.blog.controller;

import com.mszlu.blog.service.TagService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by away
 * @date 2022/1/5 16:31
 */
@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;


    @GetMapping("hot")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll() {
        return tagService.findAll();
    }
}
