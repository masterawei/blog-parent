package com.mszlu.blog.controller;

import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author by away
 * @date 2022/1/7 21:56
 */

@RestController
@RequestMapping("comments")

public class CommentsController {


    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        return commentsService.commentsByArticleId(id);
    }
}
