package com.mszlu.blog.service;

import com.mszlu.blog.vo.CategoryVo;

import java.util.List;

/**
 * @author by away
 * @date 2022/1/7 14:36
 */
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);
}
