package com.zhuanyi.hexo.admin.service.impl;

import com.zhuanyi.hexo.admin.dao.ArticleDao;
import com.zhuanyi.hexo.admin.obj.dto.ArticleDTO;
import com.zhuanyi.hexo.admin.obj.pojo.Article;
import com.zhuanyi.hexo.admin.obj.vo.ArticleListVO;
import com.zhuanyi.hexo.admin.obj.vo.ArticleVO;
import com.zhuanyi.hexo.admin.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("defaultArticleService")
public class DefaultArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao defaultArticleDao;

    @Override
    public ArticleListVO findAllArticles(Integer page, Integer limit) {
        List<Article> articles = defaultArticleDao.findAllArticles();
        return paging(articles, page, limit);
    }

    @Override
    public ArticleListVO findAllTmpArticles(Integer page, Integer limit) {
        List<Article> articles = defaultArticleDao.findAllTmpArticles();
        return paging(articles, page, limit);
    }

    private ArticleListVO paging(List<Article> articles, Integer page, Integer limit) {
        Collections.sort(articles);
        int startIndex = Math.min((page - 1) * limit, articles.size());
        int endIndex = Math.min(page * limit, articles.size());
        int no = startIndex + 1;
        int total = articles.size();

        articles = articles.subList(startIndex, endIndex);
        List<ArticleVO> articleVOS = new ArrayList<>();
        for (Article article : articles) {
            ArticleVO articleVO = new ArticleVO(article);
            articleVO.setNo(no++);
            articleVOS.add(articleVO);
        }
        return new ArticleListVO(articleVOS, total);
    }

    @Override
    public ArticleVO findArticleById(Long id) {
        Article article = defaultArticleDao.findArticleById(id);
        if (article == null) {
            article = defaultArticleDao.findTmpArticleById(id);
        }
        return new ArticleVO(article);
    }

    @Override
    public boolean create(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        if (defaultArticleDao.saveArticle(article)) {
            return defaultArticleDao.deleteTmpArticleById(article.getId());
        }
        return false;
    }

    @Override
    public boolean update(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        fillArticleInfo(article);
        if (defaultArticleDao.updateArticle(article)) {
            return defaultArticleDao.deleteTmpArticleById(article.getId());
        }
        return false;
    }

    private void fillArticleInfo(Article article) {
        Article oldArticle = defaultArticleDao.findArticleById(article.getId());
        if (oldArticle == null) {
            return;
        }
        if (CollectionUtils.isEmpty(article.getTags())) {
            article.setTags(oldArticle.getTags());
        }
        if (CollectionUtils.isEmpty(article.getCategories())) {
            article.setCategories(oldArticle.getCategories());
        }
        if (StringUtils.isEmpty(article.getContent())) {
            article.setContent(oldArticle.getContent());
        }
        if (StringUtils.isEmpty(article.getCover())) {
            article.setCover(oldArticle.getCover());
        }
        if (StringUtils.isEmpty(article.getKeyWords())) {
            article.setKeyWords(oldArticle.getKeyWords());
        }
        if (StringUtils.isEmpty(article.getTitle())) {
            article.setTitle(oldArticle.getTitle());
        }
        if (StringUtils.isEmpty(article.getDescribe())) {
            article.setDescribe(oldArticle.getDescribe());
        }
        if (StringUtils.isEmpty(article.getStatus())) {
            article.setStatus(oldArticle.getStatus());
        }
        if (StringUtils.isEmpty(article.getAuthor())) {
            article.setAuthor(oldArticle.getAuthor());
        }
        article.setPublishTime(oldArticle.getPublishTime());
    }

    @Override
    public boolean deleteById(Long id) {
        return defaultArticleDao.deleteArticleById(id);
    }

    @Override
    public boolean tmpSave(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        return defaultArticleDao.tmpSaveArticle(articleDTO);
    }

}
