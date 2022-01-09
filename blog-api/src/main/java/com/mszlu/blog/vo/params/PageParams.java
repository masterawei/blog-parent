package com.mszlu.blog.vo.params;

import lombok.Data;

/**
 * @author by away
 * @date 2022/1/5 13:20
 */
@Data
public class PageParams {
    private int page=1;
    private int pageSize =10;
    private Long categoryId;
    private Long tagId;
}
