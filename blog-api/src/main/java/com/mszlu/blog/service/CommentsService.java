package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;

/**
 * @author by away
 * @date 2022/1/7 22:01
 */
public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);
}
