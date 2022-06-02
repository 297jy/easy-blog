package com.zhuanyi.hexo.admin.controller;

import com.zhuanyi.hexo.admin.obj.dto.ArticleDTO;
import com.zhuanyi.hexo.admin.obj.form.ArticleForm;
import com.zhuanyi.hexo.admin.obj.vo.ArticleListVO;
import com.zhuanyi.hexo.admin.obj.vo.ArticleVO;
import com.zhuanyi.hexo.admin.service.ArticleService;
import com.zhuanyi.hexo.common.dto.Result;
import com.zhuanyi.hexo.base.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService defaultArticleService;

    @PostMapping("/create")
    public Result create(@RequestBody ArticleForm articleForm) {
        boolean result;
        if (articleForm.getId() == null) {
            result = defaultArticleService.create(new ArticleDTO(articleForm));
        } else {
            result = defaultArticleService.update(new ArticleDTO(articleForm));
        }
        return result ? ResultUtils.success() : ResultUtils.error();
    }

    @PostMapping("/tmp-save")
    public Result tmpSave(@RequestBody ArticleForm articleForm) {
        ArticleDTO articleDTO = new ArticleDTO(articleForm);
        boolean result = defaultArticleService.tmpSave(articleDTO);
        Long id = articleDTO.getId();
        return result ? ResultUtils.success(id) : ResultUtils.error();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Long id) {
        ArticleVO articleVO = defaultArticleService.findArticleById(id);
        return articleVO != null ? ResultUtils.success(articleVO) : ResultUtils.error();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer limit) {
        ArticleListVO articleListVO = defaultArticleService.findAllArticles(page, limit);
        return articleListVO != null ? ResultUtils.success(articleListVO) : ResultUtils.error();
    }

    @GetMapping("/tmp-list")
    public Result tmpList(@RequestParam Integer page, @RequestParam Integer limit) {
        ArticleListVO articleListVO = defaultArticleService.findAllTmpArticles(page, limit);
        return articleListVO != null ? ResultUtils.success(articleListVO) : ResultUtils.error();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Long id) {
        boolean result = defaultArticleService.deleteById(id);
        return result ? ResultUtils.success() : ResultUtils.error();
    }

}
