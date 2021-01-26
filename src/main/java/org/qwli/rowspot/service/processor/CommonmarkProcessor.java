package org.qwli.rowspot.service.processor;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author qwli7
 * commonmark markdown 解析
 */
@Component
public class CommonmarkProcessor implements MarkdownProcessor {

    /**
     * HtmlRender 解析
     */
    private final HtmlRenderer renderer;

    /**
     * Parser
     */
    private final Parser parser;

    public CommonmarkProcessor() {
        //表格扩展
        //标题生成锚点
        //任务列表扩展
        List<Extension> extensions = Arrays.asList(
                //表格扩展
//                TablesExtension.create(),

                AutolinkExtension.create(),

                //标题生成锚点
                HeadingAnchorExtension.create(),

                //任务列表扩展
                TaskListItemsExtension.create());
        this.renderer = HtmlRenderer.builder().extensions(extensions).build();
        this.parser = Parser.builder().extensions(extensions).build();
    }


    @Override
    public String process(String markdown) {
        if (StringUtils.isEmpty(markdown)) {
            return "";
        }
        Node node = parser.parse(markdown);

//        WordCountVisitor visitor = new WordCountVisitor();
//        node.accept(visitor);

        return renderer.render(node);
    }

    @Override
    public Map<Integer, String> processMap(Map<Integer, String> markdownMap) {
        if (CollectionUtils.isEmpty(markdownMap)) {
            return Collections.emptyMap();
        }

        markdownMap.replaceAll((i, v) -> process(markdownMap.get(i)));

        return markdownMap;
    }
}
