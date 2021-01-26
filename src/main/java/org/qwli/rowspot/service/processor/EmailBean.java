package org.qwli.rowspot.service.processor;

import java.io.Serializable;
import java.util.List;


/**
 * 邮件 Bean
 *
 * @author liqiwen
 * @since 1.2
 */
public class EmailBean implements Serializable {

    /**
     * 邮件来自于
     */
    private String from;

    /**
     * 邮件发送给 xx
     */
    private List<String> sendTos;

    /**
     * 设置主题
     */
    private String subject;

    /**
     * 设置内容
     */
    private String content;

    /**
     * 是否是 html
     */
    private Boolean isHtml;

    /**
     * 是否是 Markdown
     */
    private Boolean isMarkdown;


    public Boolean getMarkdown() {
        return isMarkdown;
    }

    public void setMarkdown(Boolean markdown) {
        isMarkdown = markdown;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getSendTos() {
        return sendTos;
    }

    public void setSendTos(List<String> sendTos) {
        this.sendTos = sendTos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getHtml() {
        return isHtml;
    }

    public void setHtml(Boolean html) {
        isHtml = html;
    }
}
