package org.qwli.rowspot.model.enums;

/**
 * @author liqiwen
 * 评论状态
 */
public enum CommentState {

    /**
     * 审核中
     */
    CHECKING("审核中"),

    /**
     * 已发布
     */
    POSTED("已发布"),

    /**
     * 已删除
     */
    DELETED("已删除");

    private final String desc;

    CommentState(String desc){
        this.desc  = desc;
    }

    public String getDesc() {
        return desc;
    }
}

