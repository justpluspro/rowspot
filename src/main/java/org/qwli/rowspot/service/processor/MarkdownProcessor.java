package org.qwli.rowspot.service.processor;


import java.util.Map;

/**
 * MarkdownProcessor 解析器接口
 */
public interface MarkdownProcessor {

    /**
     * 解析 markdown 接口
     *
     * @param markdown markdown
     * @return string
     */
    String process(String markdown);

    /**
     * 批量解析 markdown 接口
     *
     * @param markdownMap markdownMap
     * @return Map<Integer, String>
     */
    Map<Integer, String> processMap(Map<Integer, String> markdownMap);
}
