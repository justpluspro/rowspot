package org.qwli.rowspot.service;


import java.util.Map;

public interface MarkdownParser {

    /**
     * 解析 markdown 接口
     *
     * @param markdown markdown
     * @return string
     */
    String parse(String markdown);

    /**
     * 批量解析 markdown 接口
     *
     * @param markdownMap markdownMap
     * @return Map<Integer, String>
     */
    Map<Integer, String> parseMap(Map<Integer, String> markdownMap);
}
