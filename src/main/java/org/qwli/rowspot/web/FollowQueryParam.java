package org.qwli.rowspot.web;

import java.io.Serializable;

public class FollowQueryParam extends UserQueryParam implements Serializable {

    private Boolean queryType;

    public Boolean getQueryType() {
        return queryType;
    }

    public void setQueryType(Boolean queryType) {
        this.queryType = queryType;
    }
}
