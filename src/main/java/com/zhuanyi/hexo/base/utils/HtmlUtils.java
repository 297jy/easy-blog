package com.zhuanyi.hexo.base.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.util.CollectionUtils;

public class HtmlUtils {

    public static String unescape(String html) {
        Document document = Jsoup.parse(html);
        for (Element e : document.getAllElements()) {
            if (e.isBlock()){
                System.out.println(e.text());
            }
            if (CollectionUtils.isEmpty(e.children())) {
                System.out.println(e.text());
                //e.text(StringEscapeUtils.escapeHtml4(e.text()));
            }
        }
        return document.toString();
    }
}
