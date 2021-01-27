package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/1/27 13:39
 * 功能：NewComment
 **/
public class NewComment implements Serializable {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
