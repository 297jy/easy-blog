package com.zhuanyi.hexo.base.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class FileUtils {

    public static List<String> readAllLinesFromFile(String path) {
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            List<String> lines = new ArrayList<>();
            String line = fileReader.readLine();
            while (line != null) {
                lines.add(line);
                line = fileReader.readLine();
            }
            return lines;
        } catch (Throwable e) {
            log.error("readAllLinesFromFile失败，{%s}", e);
            return null;
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    log.error("关闭fileReader失败，{%s}", e);
                }
            }
        }
    }

    public static boolean writeContentToFile(String path, String content) {
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            fileWriter.write(content);
            return true;
        } catch (Throwable e) {
            log.error("writeContentToFile失败，{%s}", e);
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    log.error("关闭fileWriter失败，{%s}", e);
                }
            }
        }
    }

    public static List<String> getFileNames(String path) {
        List<String> files = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList == null) {
            return Collections.emptyList();
        }

        for (File value : tempList) {
            if (value.isFile()) {
                files.add(value.getName());
            }
        }
        return files;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    public static boolean existFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean moveFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (!oldFile.exists()) {
            log.error("文件未检测到，结束程序,oldPath:{}", oldPath);
            return false;
        }
        return oldFile.renameTo(newFile);
    }

    public static void main(String[] args) throws IOException {
        //String html = StringUtils.join(readAllLinesFromFile("C:\\Users\\Administrator\\Desktop\\myblog\\source\\_posts\\4.md"), "\r\n");
        String html = "  <pre><code>dsf\r\n</code></pre> ";
        //String pattern = "<pre><code\\s+class=\"language-(\\w+)\">.*</code></pre>";
        String pattern = "<pre>[\\s\r\n]*<code\\s*(class=\"language-(\\w+)\")?>(((?!</pre>).)*)</code>[\\s\r\n]*</pre>";
        Pattern compile = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = compile.matcher(html);
        while (matcher.find()) {
            String language = matcher.group(2);
            if (StringUtils.isEmpty(language)){
                language = "java";
            }
            html = html.replace(matcher.group(), String.format("\r\n```%s\r\n%s\r\n```\r\n", language, matcher.group(3)));
            System.out.println(html);
            break;
        }
        pattern = "\r\n```(\\w+)\r\n(((?!```).)*)\r\n```\r\n";
        compile = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        matcher = compile.matcher(html);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            html = html.replace(matcher.group(), String.format("<pre><code class=\"language-%s\">%s</code></pre>", matcher.group(1), matcher.group(2)));
            System.out.println(html);
            break;
        }
        //System.out.println(targetList);
    }

    private static void dfs(Element element) {
        Elements es = element.getElementsByAttributeValue("class", "language-java");

    }

    private static String render() {
        return null;
    }
}
