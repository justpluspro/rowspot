package org.qwli.rowspot.event;

import java.util.Date;

public interface DomainEvent {

    String eventVersion();

    Date occurredOn();

}
