package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Tag;

import java.util.List;

/**
 * @author by away
 * @date 2022/1/5 11:10
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 通过文章id返回标签列表
     * @param articleId
     * @return
     */

    List<Tag> findTagsByArticleId(Long articleId);
}
