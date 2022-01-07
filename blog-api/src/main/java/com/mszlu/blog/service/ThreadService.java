package com.mszlu.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author by away
 * @date 2022/1/7 21:13
 */
@Service
public class ThreadService {

    /**
     * 希望此操作在线程池执行,不会影响原有主线程
     *
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置  为了在多线程的环境下线程安全
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        //最终的sql为  update article set viewCount=100 where viewCount=99 and id = article.getId();
        // 在多线程情况下,保证+1操作正确
        articleMapper.update(articleUpdate,updateWrapper);

        try {
            Thread.sleep(5000);
            System.out.println("更新完成了....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
