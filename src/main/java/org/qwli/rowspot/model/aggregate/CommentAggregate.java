package org.qwli.rowspot.model.aggregate;

import java.io.Serializable;
import java.util.Map;

public class CommentAggregate implements Serializable {

    private Long id;

    private boolean checking;

    private String content;

    private String createAt;

    private Map<String, String> user;

    private Map<String, String> article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Map<String, String> getUser() {
        return user;
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public Map<String, String> getArticle() {
        return article;
    }

    public void setArticle(Map<String, String> article) {
        this.article = article;
    }
}
