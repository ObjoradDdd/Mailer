package com.github.objoradddd.mailer.adapters.out.mail;

import com.github.objoradddd.mailer.application.exception.MailDeliveryException;
import com.github.objoradddd.mailer.application.port.out.EmailSenderPort;
import com.github.objoradddd.mailer.domain.model.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender mailSender;

    public SmtpEmailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(EmailMessage message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), message.isHtml());

            if (!message.getCc().isEmpty()) {
                helper.setCc(message.getCc().toArray(String[]::new));
            }
            if (!message.getBcc().isEmpty()) {
                helper.setBcc(message.getBcc().toArray(String[]::new));
            }
            if (message.getReplyTo() != null && !message.getReplyTo().isBlank()) {
                helper.setReplyTo(message.getReplyTo());
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException | MailException ex) {
            throw new MailDeliveryException("Failed to send email via SMTP", ex);
        }
    }
}
