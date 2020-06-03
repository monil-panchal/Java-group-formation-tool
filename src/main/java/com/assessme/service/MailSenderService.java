package com.assessme.service;

import com.assessme.SystemConfig;
import com.assessme.config.EmailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 11:33 AM
 */
@Service
public class MailSenderService {

    private Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    private static MailSenderService instance;
    private JavaMailSenderImpl mailSender;

    public MailSenderService(){
        mailSender = getConfigured();
    }

    public static MailSenderService getInstance() {
        if (instance == null) {
            instance = new MailSenderService();
        }
        return instance;
    }

    private JavaMailSenderImpl getConfigured() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        EmailConfig emailConfig = SystemConfig.getInstance().getEmailConfig();
        javaMailSender.setHost(emailConfig.getHost());
        javaMailSender.setPort(emailConfig.getPort());
        javaMailSender.setUsername(emailConfig.getUsername());
        javaMailSender.setPassword(emailConfig.getPassword());
        javaMailSender.setJavaMailProperties(emailConfig.getProps());
        return javaMailSender;
    }

    @Async
    public void sendSimpleMessage(String to, String subject, String text) {
        logger.info(String.format("Sending Mail to %s", to));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        logger.info(String.format("Mail Sent Successfully to %s", to));
    }
}
