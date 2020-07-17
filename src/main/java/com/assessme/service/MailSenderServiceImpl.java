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
public class MailSenderServiceImpl implements MailSenderService {

    private static MailSenderServiceImpl instance;
    private final Logger logger = LoggerFactory.getLogger(MailSenderServiceImpl.class);
    private final EmailConfig emailConfig;
    private final JavaMailSenderImpl mailSender;

    public MailSenderServiceImpl() {
        emailConfig = SystemConfig.getInstance().getEmailConfig();
        mailSender = getConfigured();
    }

    public static MailSenderServiceImpl getInstance() {
        if (instance == null) {
            instance = new MailSenderServiceImpl();
        }
        return instance;
    }

    @Override
    public JavaMailSenderImpl getConfigured() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfig.getHost());
        javaMailSender.setPort(emailConfig.getPort());
        javaMailSender.setUsername(emailConfig.getUsername());
        javaMailSender.setPassword(emailConfig.getPassword());
        javaMailSender.setJavaMailProperties(emailConfig.getProps());
        return javaMailSender;
    }

    @Override
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
