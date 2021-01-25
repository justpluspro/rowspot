package org.qwli.rowspot.model.enums;

/**
 * @author liqiwen
 * 文章状态
 */
public enum ArticleState {

    /**
     * 草稿
     */
    DRAFT("草稿"),

    /**
     * 定时发布
     */
    SCHEDULED("定时发布"),

    /**
     * 已发布
     */
    POSTED("已发布"),

    /**
     * 已删除
     */
    DELETED("已删除");

    /**
     * 内容描述
     */
    private final String desc;

    ArticleState(String desc){
        this.desc  = desc;
    }

    public String getDesc() {
        return desc;
    }
}

