package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.dao.dos.Archives;
import com.mszlu.blog.dao.mapper.ArticleBodyMapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.mapper.ArticleTagMapper;
import com.mszlu.blog.dao.pojo.*;
import com.mszlu.blog.service.*;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.ArticleBodyVo;
import com.mszlu.blog.vo.ArticleVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;
import com.mszlu.blog.vo.params.ArticleParam;
import com.mszlu.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by away
 * @date 2022/1/5 13:29
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleTagMapper articleTagMapper;


    /**
     * 因为有两个请求都需要使用到这个方法,只需要进行一个条件判断,就可以进行区分,具体如下
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));


    }
    //@Override
    //public Result listArticle(PageParams pageParams) {
    //    Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
    //    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    //
    //    if (pageParams.getCategoryId() != null) {
    //        queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
    //    }
    //    List<Long> articleIdList = new ArrayList<>();
    //    if (pageParams.getTagId() != null) {
    //        //文章里面没有tagId这个属性,需要通过额外的表来查询
    //        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
    //        articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
    //        List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
    //        for (ArticleTag articleTag : articleTags) {
    //            articleIdList.add(articleTag.getArticleId());
    //        }
    //        if (articleIdList.size() > 0) {
    //            // and id in (1,2,3)
    //            queryWrapper.in(Article::getId,articleIdList);
    //        }
    //    }
    //    ///是否置顶
    //    //order by create_date
    //    queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
    //    Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
    //    List<Article> records = articlePage.getRecords();
    //    //能直接返回吗？不能！
    //    List<ArticleVo> articleVoList = copyList(records,true,true);
    //
    //    return Result.success(articleVoList);
    //}

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,title from article order by view_counts desc limit 5
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,title from article order by create_date desc limit 5
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        // 文章归档
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;


    /**
     * 1.根据id查询文章信息
     * 2.根据bodyId和categoryId去关联查询
     * 3.生成articleBodyVo和CategoryVo
     */
    @Override
    public Result findArticleById(Long articleId) {

        Article article = this.articleMapper.selectById(articleId);

        ArticleVo articleVo = copy(article, true, true,true,true);

        //查看完文章后,新增阅读数,有没有问题呢?
        //查看万文章之后,本应该直接返回数据了,这个时候做了一个更新操作,更新时加写锁,阻塞其他读操作,性能就会比较低
        //更新增加了此次接口,耗时  如果一旦更新出现问题,不能影响查看文章的操作
        //线程池 可以把更新操作,扔到线程池中去执行,和主线程就不相关了
        threadService.updateArticleViewCount(articleMapper,article);

        return Result.success(articleVo);
    }

    /**
     * 1.发布文章,目的构建Article对象
     * 2.作者id: 当前登录用户(前提是当前用户登陆了,所以需要把写文章放入拦截器)
     * 3.标签tag: 要将标签加入到关联列表中
     * 4.内存body:
     *
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(articleParam.getCategory().getId());
        articleMapper.insert(article);
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());

        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }


    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;


    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签，作者信息
        if (isTag) {
            Long articleId = article.getId();
            System.out.println(articleId);
            List<TagVo> tagVoList = tagService.findTagsByArticleId(articleId);
            articleVo.setTags(tagVoList);
        }


        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }

        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


}
