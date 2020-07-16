package com.assessme.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Darshan Kathiriya
 * @created 13-June-2020 7:52 PM
 */
public interface MailSenderService {

    JavaMailSenderImpl getConfigured();

    @Async
    void sendSimpleMessage(String to, String subject, String text);
}
