package org.qwli.rowspot.event;

/**
 * @author liqiwen
 * @since 1.2
 * 取消关注事件
 */
public class UnFollowEvent extends AbstractEvent {

    public UnFollowEvent(Object source) {
        super(source, EventType.UN_FOLLOWED);
    }
}
