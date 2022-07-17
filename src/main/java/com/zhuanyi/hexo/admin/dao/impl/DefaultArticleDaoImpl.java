package com.zhuanyi.hexo.admin.dao.impl;

import com.zhuanyi.hexo.admin.dao.ArticleDao;
import com.zhuanyi.hexo.admin.obj.pojo.Article;
import com.zhuanyi.hexo.base.hexo.HexoFileManage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("defaultArticleDao")
public class DefaultArticleDaoImpl implements ArticleDao {

    @Resource
    private HexoFileManage defaultHexoFileManage;

    @Override
    public Article findArticleById(Long id) {
        return defaultHexoFileManage.readArticleById(id);
    }

    @Override
    public Article findTmpArticleById(Long id) {
        return defaultHexoFileManage.readTmpArticleById(id);
    }

    @Override
    public List<Article> findAllArticles() {
        return defaultHexoFileManage.readAllArticles();
    }

    @Override
    public List<Article> findAllTmpArticles() {
        return defaultHexoFileManage.readAllTmpArticles();
    }

    @Override
    public boolean saveArticle(Article article) {
        Long nextId = defaultHexoFileManage.getNewArticleId();
        article.setId(nextId);
        return defaultHexoFileManage.saveArticle(article);
    }

    @Override
    public boolean tmpSaveArticle(Article article) {
        return defaultHexoFileManage.tmpSaveArticle(article);
    }

    @Override
    public boolean updateArticle(Article article) {
        return defaultHexoFileManage.saveArticle(article);
    }

    @Override
    public boolean deleteArticleById(Long id) {
        return defaultHexoFileManage.deleteArticleById(id);
    }

    @Override
    public boolean deleteTmpArticleById(Long id) {
        return defaultHexoFileManage.deleteTmpArticleById(id);
    }

}
