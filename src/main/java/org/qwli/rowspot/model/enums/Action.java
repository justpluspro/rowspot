package org.qwli.rowspot.model.enums;

public enum Action {
    PROPOSE("提出"),
    COLLECT("收藏"),
    ANSWER("回答"),
    FOLLOW("关注"),
    PUBLISH("发表")
    ;


    String desc;

    Action(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
