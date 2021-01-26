package org.qwli.rowspot.service.processor;

import java.util.List;

/**
 * 邮件处理器
 * @author liqiwen
 * @since 1.2
 */
public interface MailProcessor {

    /**
     * 发送单个 Email 邮件
     * @param emailBean emailBean
     */
    default void sendHtmlMail(EmailBean emailBean) {

    }

    /**
     * 发送单个文本文件
     * @param emailBean emailBean
     */
    default void sendTextMail(EmailBean emailBean) {

    }

    /**
     * 发送多个 html 邮件
     * @param emailBeans emailBeans
     */
    default void sendHtmlMails(List<EmailBean> emailBeans) {

    }

    /**
     * 发送模板邮件
     * @param emailBean emailBean
     */
    default void sendTemplateMail(EmailBean emailBean) {

    }

}
