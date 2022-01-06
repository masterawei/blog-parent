package com.mszlu.blog.controller;

import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by away
 * @date 2022/1/5 11:11
 * json数据进行交互
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {

        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }
}
