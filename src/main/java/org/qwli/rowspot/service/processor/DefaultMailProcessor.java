package org.qwli.rowspot.service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邮件处理器
 * @author liqiwen
 */
@Component
public class DefaultMailProcessor implements MailProcessor, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Resource
    private JavaMailSender javaMailSender;

    private final MarkdownProcessor markdownProcessor;


    public DefaultMailProcessor(MarkdownProcessor markdownProcessor) {
        this.markdownProcessor = markdownProcessor;
    }

    @Override
    public void sendTextMail(EmailBean emailBean) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailBean.getFrom());
            simpleMailMessage.setTo((String[]) emailBean.getSendTos().toArray());
            simpleMailMessage.setSubject(emailBean.getSubject());
            simpleMailMessage.setText(emailBean.getContent());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception ex){
            logger.error("sendTextMail error: [{}]", ex.getMessage(), ex);
        }
    }

    @Override
    public void sendHtmlMail(EmailBean emailBean) {
        if(emailBean == null) {
            return;
        }
        if(CollectionUtils.isEmpty(emailBean.getSendTos())) {
            return;
        }
        try {
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailBean.getFrom());
            final List<String> rightEmails = emailBean.getSendTos().stream().filter(e -> e.contains("@")).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(rightEmails)) {
                return;
            }
            helper.setTo(((String[]) rightEmails.toArray()));
            helper.setSubject(emailBean.getSubject());
            final Boolean html = emailBean.getHtml();
            if (html) {
                helper.setText(emailBean.getContent(), true);
            } else if (emailBean.getMarkdown()) {
                helper.setText(markdownProcessor.process(emailBean.getContent()));
            } else {
                helper.setText(emailBean.getContent(), false);
            }
            javaMailSender.send(mimeMessage);
        } catch (Exception ex){
            logger.error("sendHtmlMail error: [{}]", ex.getMessage(), ex);
        }
    }

    @Override
    public void destroy() throws Exception {
        logger.error("DefaultMailProcessor destroy");
    }
}
