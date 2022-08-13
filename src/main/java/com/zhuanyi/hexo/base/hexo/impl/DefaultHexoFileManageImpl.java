package com.zhuanyi.hexo.base.hexo.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("defaultHexoFileManage")
public class DefaultHexoFileManageImpl extends AbstractHexoFileManageImpl {

    @Override
    public String getArticleAuthor(List<String> hexoContentLines) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }

        String titleLine = hexoContentLines.stream().filter(line -> line.trim().startsWith("author:"))
                .findFirst().orElse("");
        return titleLine.replace("author:", "").trim();
    }

    @Override
    public String getArticleTitle(List<String> hexoContentLines) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }

        String titleLine = hexoContentLines.stream().filter(line -> line.trim().startsWith("title:"))
                .findFirst().orElse("");
        return titleLine.replace("title:", "").trim();
    }

    @Override
    public List<String> getArticleTags(List<String> hexoContentLines) {
        return findAllLabels(hexoContentLines, "tags:");
    }

    @Override
    public List<String> getArticleCategories(List<String> hexoContentLines) {
        return findAllLabels(hexoContentLines, "categories:");
    }

    @Override
    public String getArticleDate(List<String> hexoContentLines) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }
        String dateLine = hexoContentLines.stream().filter(line -> line.trim().startsWith("date:"))
                .findFirst().orElse("");
        return dateLine.replace("date:", "").trim();
    }

    @Override
    public String getArticleKeywords(List<String> hexoContentLines) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }
        String keyWordsLine = hexoContentLines.stream().filter(line -> line.trim().startsWith("keywords:"))
                .findFirst().orElse("");
        return keyWordsLine.replace("keywords:", "").trim();
    }

    @Override
    public String getArticleCover(List<String> hexoContentLines) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }
        String keyWordsLine = hexoContentLines.stream().filter(line -> line.trim().startsWith("cover:"))
                .findFirst().orElse("");
        return keyWordsLine.replace("cover:", "").trim();
    }

    @Override
    public String getArticleContent(List<String> hexoContentLines) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }
        int index = hexoContentLines.lastIndexOf("---");
        if (index != -1) {
            int startIndex = Math.min(index + 1, hexoContentLines.size());
            return StringUtils.join(hexoContentLines.subList(startIndex, hexoContentLines.size()), "\r\n");
        }
        return "";
    }

    @Override
    public List<String> getHexoAuthor(String author) {
        return Collections.singletonList("author: " + author);
    }

    @Override
    public List<String> getHexoTitle(String title) {
        return Collections.singletonList("title: " + title);
    }

    @Override
    public List<String> getHexoTags(List<String> tags) {
        List<String> hexoTags = new ArrayList<>();
        hexoTags.add("tags:");
        for (String tag : tags) {
            hexoTags.add("  - " + tag);
        }
        return hexoTags;
    }

    @Override
    public List<String> getHexoCategories(List<String> categories) {
        List<String> hexoCategories = new ArrayList<>();
        hexoCategories.add("categories:");
        for (String category : categories) {
            hexoCategories.add("  - " + category);
        }
        return hexoCategories;
    }

    @Override
    public List<String> getHexoDate(String date) {
        return Collections.singletonList("date: " + date);
    }

    @Override
    public List<String> getHexoCover(String cover) {
        return Arrays.asList("cover: " + cover, "top_img: " + cover);
    }

    @Override
    public List<String> getHexoKeyWords(String keywords) {
        return Collections.singletonList("keywords: " + keywords);
    }

    @Override
    public List<String> getHexoContent(String content) {
        return Collections.singletonList(content);
    }

    @Override
    protected String getHexoContentEncode(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        String pattern = "<pre>[\\s\r\n]*<code\\s*(class=\"language-(\\w+)\")?>(((?!</pre>).)*)</code>[\\s\r\n]*</pre>";
        Pattern compile = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = compile.matcher(content);
        while (matcher.find()) {
            String language = matcher.group(2);
            if (StringUtils.isEmpty(language)) {
                language = "code";
            }
            content = content.replace(matcher.group(), String.format("\r\n```%s\r\n%s\r\n```\r\n",
                    language, StringEscapeUtils.unescapeHtml4(matcher.group(3))));
        }
        return content;
    }

    @Override
    protected String getHexoContentDecode(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }

        String pattern = "\r\n```(\\w+)\r\n(((?!```).)+)\r\n```\r\n";
        //String pattern = "\r\n```(\\w+)\r\n(.+)```\r\n";
        Pattern compile = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = compile.matcher(content);
        while (matcher.find()) {
            content = content.replace(matcher.group(), String.format("<pre><code class=\"language-%s\">%s</code></pre>",
                    matcher.group(1), StringEscapeUtils.escapeHtml4(matcher.group(2))));
        }

        return content;
    }

    private List<String> findAllLabels(List<String> hexoContentLines, String label) {
        if (CollectionUtils.isEmpty(hexoContentLines)) {
            return null;
        }

        List<String> labels = new ArrayList<>();
        for (int i = 0; i < hexoContentLines.size() - 1; i++) {
            if (hexoContentLines.get(i).trim().startsWith(label)) {
                int j = i + 1;
                while (j < hexoContentLines.size() && hexoContentLines.get(j).trim().startsWith("-")) {
                    String tag = hexoContentLines.get(j).trim().replace("-", "").trim();
                    labels.add(tag);
                    j++;
                }
                return labels;
            }
        }
        return labels;
    }
}
