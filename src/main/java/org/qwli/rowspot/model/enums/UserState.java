package org.qwli.rowspot.model.enums;

/**
 * 用户状态
 * @author liqiwen
 */
public enum UserState {

    /**
     * 已锁定
     */
    LOCKED("已锁定"),

    /**
     * 正常
     */
    NORMAL("正常"),

    /**
     * 未激活
     */
    UNACTIVATED("未激活"),
    ;

    private final String desc;

    UserState(String desc){

        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
