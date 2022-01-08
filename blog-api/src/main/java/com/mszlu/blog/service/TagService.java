package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;

import java.util.List;

/**
 * @author by away
 * @date 2022/1/5 13:55
 */
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 所有文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();
}
