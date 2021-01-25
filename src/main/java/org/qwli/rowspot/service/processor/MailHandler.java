package org.qwli.rowspot.service.processor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Component
public class MailHandler {

    @Resource
    private JavaMailSender javaMailSender;


    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("228794754@qq.com");
        message.setTo("228794754@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        javaMailSender.send(message);
    }


    public void sendHtmlMail() throws Exception {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("228794754@qq.com");
        helper.setTo("228794754@qq.com");
        helper.setSubject("标题：发送 html 内容");

        StringBuffer sb = new StringBuffer();
        sb.append("<h1>内容标题</h1>")
                .append("<p><a href='javascript:void(0);'>点我激活</a></p>");

        helper.setText(sb.toString(), true);

        javaMailSender.send(mimeMessage);
    }
}
