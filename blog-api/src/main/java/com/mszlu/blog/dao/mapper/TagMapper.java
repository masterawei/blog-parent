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

    /**
     * 查询最热门的标签 前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据id查标签
     * @return
     * @param tagIds
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
