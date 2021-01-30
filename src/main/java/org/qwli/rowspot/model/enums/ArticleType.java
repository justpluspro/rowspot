package org.qwli.rowspot.model.enums;

import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.model.aggregate.TypeAggregate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qwli7
 */

public enum ArticleType {
    /**
     * 文章类型
     */
    A("index", "教程",0),

    /**
     * 问答
     */
    Q("issues", "问答区", 1),

    /**
     * 动态
     */
    N("news", "最新动态",2),

    /**
     * 工具类
     */
    T("tools", "实用工具", 3),

    /**
     * 首页唯一
     */
    I("index_unique", "",-1);

    String code;
    String name;
    Integer sort;

    ArticleType(String code, String name, Integer sort) {
        this.code = code;
        this.sort = sort;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getSort() {
        return sort;
    }

    public static boolean isArticle(String name) {
        return A.name().equals(name);
    }

    public static boolean isQuestion(String name) {
        return Q.name().equals(name);
    }

    public static boolean isIndexUnique(String name) {
        return I.name().equals(name);
    }

    public static boolean isNews(String name) {
        return N.name().equals(name);
    }

    public static boolean isTools(String name) {
        return T.name().equals(name);
    }
       
    /**
     * 获取所有的文章类型，并排序
     **/
    public static List<TypeAggregate> findAll(Category category, ArticleType type) {
        ArticleType[] values = ArticleType.values();
        return Arrays.stream(values).filter(e -> e.getSort() >= 0).sorted(Comparator.comparingInt(ArticleType::getSort)).map(e -> {
            TypeAggregate typeAggregate = new TypeAggregate();
            typeAggregate.setName(e.getName());
            typeAggregate.setAlias(category.getAlias() + "/" + e.getCode());
            typeAggregate.setChecked(type == e);
            return typeAggregate;
        }).collect(Collectors.toList());
    }
}
