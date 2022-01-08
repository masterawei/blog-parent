package com.mszlu.blog.dao.pojo;

import lombok.Data;

/**
 * @author by away
 * @date 2022/1/8 13:02
 */
@Data
public class ArticleTag {
    private Long id;
    private  Long articleId;
    private Long tagId;
}
