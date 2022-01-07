package com.mszlu.blog.dao.pojo;

import lombok.Data;

/**
 *
 * @author away
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}

