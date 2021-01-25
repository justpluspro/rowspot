package org.qwli.rowspot.model.enums;

import org.springframework.context.ApplicationEvent;

public class AbstractEvent<T> extends ApplicationEvent {


    private final T event;

    public AbstractEvent(Object source, T event) {
        super(source);
        this.event = event;
    }

    public T getEvent() {
        return event;
    }
}
