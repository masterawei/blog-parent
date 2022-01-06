package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.dos.Archives;
import com.mszlu.blog.dao.pojo.Article;

import java.util.List;

/**
 * @author by away
 * @date 2022/1/5 11:08
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
