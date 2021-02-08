package org.qwli.rowspot.event;

import org.springframework.context.ApplicationEvent;

/**
 * 基础事件类型
 * @author liqiwen
 * @since 1.2
 */
public class AbstractEvent extends ApplicationEvent {

    /**
     * 事件类型
     */
    private final EventType eventType;

    public AbstractEvent(Object source, EventType eventType) {
        super(source);
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }
}
