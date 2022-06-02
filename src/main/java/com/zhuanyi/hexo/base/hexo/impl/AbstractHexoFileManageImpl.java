package com.zhuanyi.hexo.base.hexo.impl;

import com.zhuanyi.hexo.admin.config.SystemConfig;
import com.zhuanyi.hexo.admin.obj.pojo.Article;
import com.zhuanyi.hexo.base.hexo.HexoFileManage;
import com.zhuanyi.hexo.base.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public abstract class AbstractHexoFileManageImpl implements HexoFileManage {

    public static final String MD_SUFFIX = ".md";

    @Resource
    private SystemConfig systemConfig;

    @Override
    public List<Article> readAllArticles() {
        return readAllArticlesFromPath(systemConfig.getHexoSourcePath());
    }

    @Override
    public List<Article> readAllTmpArticles() {
        return readAllArticlesFromPath(systemConfig.getHexoTmpSourcePath());
    }

    @Override
    public Article readArticleById(Long id) {
        return readArticleFromFile(systemConfig.getHexoSourcePath(), id);
    }

    @Override
    public Article readTmpArticleById(Long id) {
        return readArticleFromFile(systemConfig.getHexoTmpSourcePath(), id);
    }

    @Override
    public boolean saveArticle(Article article) {
        Long id = article.getId();
        String path = systemConfig.getHexoSourcePath() + File.separator + id + ".md";
        return saveArticleToFile(article, path);
    }

    @Override
    public boolean tmpSaveArticle(Article article) {
        Long id = article.getId();
        String path = systemConfig.getHexoTmpSourcePath() + File.separator + id + ".md";
        return saveArticleToFile(article, path);
    }

    @Override
    public Long getNewArticleId() {
        long maxId = 0L;
        List<String> titles = FileUtils.getFileNames(systemConfig.getHexoSourcePath());
        for (String title : titles) {
            if (title.endsWith(MD_SUFFIX)) {
                long id = Long.parseLong(title.replace(MD_SUFFIX, ""));
                maxId = Math.max(maxId, id);
            }
        }
        return maxId + 1;
    }

    @Override
    public boolean deleteArticleById(Long id) {
        String path = systemConfig.getHexoSourcePath() + File.separator + id + MD_SUFFIX;
        String newPath = systemConfig.getHexoRemoveSourcePath() + File.separator + id + MD_SUFFIX;
        return FileUtils.moveFile(path, newPath);
    }

    @Override
    public boolean deleteTmpArticleById(Long id) {
        String path = systemConfig.getHexoTmpSourcePath() + File.separator + id + MD_SUFFIX;
        return FileUtils.deleteFile(path);
    }

    public abstract String getArticleAuthor(List<String> hexoContentLines);

    public abstract String getArticleTitle(List<String> hexoContentLines);

    public abstract List<String> getArticleTags(List<String> hexoContentLines);

    public abstract List<String> getArticleCategories(List<String> hexoContentLines);

    public abstract String getArticleDate(List<String> hexoContentLines);

    public abstract String getArticleKeywords(List<String> hexoContentLines);

    public abstract String getArticleCover(List<String> hexoContentLines);

    public abstract String getArticleContent(List<String> hexoContentLines);

    public abstract List<String> getHexoAuthor(String author);

    public abstract List<String> getHexoTitle(String title);

    public abstract List<String> getHexoTags(List<String> tags);

    public abstract List<String> getHexoCategories(List<String> categories);

    public abstract List<String> getHexoDate(String date);

    public abstract List<String> getHexoCover(String cover);

    public abstract List<String> getHexoKeyWords(String keywords);

    public abstract List<String> getHexoContent(String content);

    protected abstract String getHexoContentEncode(String content);

    protected abstract String getHexoContentDecode(String content);

    public List<Article> readAllArticlesFromPath(String path) {
        List<Article> articles = new ArrayList<>();
        List<String> titles = FileUtils.getFileNames(path);
        for (String title : titles) {
            if (title.endsWith(MD_SUFFIX)) {
                Long id = Long.valueOf(title.replace(MD_SUFFIX, ""));
                Article article = readArticleById(id);
                if (article != null) {
                    articles.add(article);
                } else {
                    log.error("读取文章失败：{}", title);
                }
            }
        }
        return articles;
    }

    private boolean saveArticleToFile(Article article, String path) {
        List<String> hexoFileLines = new ArrayList<>();
        hexoFileLines.add("---");
        hexoFileLines.addAll(getHexoAuthor(article.getAuthor()));
        hexoFileLines.addAll(getHexoTitle(article.getTitle()));
        hexoFileLines.addAll(getHexoTags(article.getTags()));
        hexoFileLines.addAll(getHexoCategories(article.getCategories()));
        hexoFileLines.addAll(getHexoCover(article.getCover()));
        hexoFileLines.addAll(getHexoKeyWords(article.getKeyWords()));
        hexoFileLines.addAll(getHexoDate(article.getPublishTime()));
        hexoFileLines.add("---");
        hexoFileLines.addAll(getHexoContent(getHexoContentEncode(article.getContent())));

        String hexoFileContent = StringUtils.join(hexoFileLines, "\r\n");
        return FileUtils.writeContentToFile(path, hexoFileContent);
    }

    private Article readArticleFromFile(String directory, Long id) {
        String path = directory + File.separator + id + ".md";
        if (!FileUtils.existFile(path)) {
            log.error(path + "文件不存在");
            return null;
        }

        Article article = new Article();
        List<String> lines = FileUtils.readAllLinesFromFile(path);
        if (CollectionUtils.isEmpty(lines)) {
            return null;
        }

        article.setId(id);
        article.setAuthor(getArticleAuthor(lines));
        article.setTitle(getArticleTitle(lines));
        article.setCategories(getArticleCategories(lines));
        article.setContent(getHexoContentDecode(getArticleContent(lines)));
        article.setCover(getArticleCover(lines));
        article.setKeyWords(getArticleKeywords(lines));
        article.setTags(getArticleTags(lines));
        article.setPublishTime(getArticleDate(lines));
        return article;
    }

}
