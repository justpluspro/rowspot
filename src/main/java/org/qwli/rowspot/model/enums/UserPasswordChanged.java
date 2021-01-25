package org.qwli.rowspot.model.enums;

import org.qwli.rowspot.event.DomainEvent;
import org.qwli.rowspot.model.User;

import java.util.Date;
import java.util.UUID;

public class UserPasswordChanged implements DomainEvent {

    private String eventVersion;

    private Date occurredOn;

    private User userId;

    private String username;



    public UserPasswordChanged(User aUserId, String aUsername) {
        this.eventVersion = UUID.randomUUID().toString();
        this.occurredOn = new Date();
        this.userId = aUserId;
        this.username = aUsername;
    }


    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String eventVersion() {
        return null;
    }

    @Override
    public Date occurredOn() {
        return null;
    }
}
