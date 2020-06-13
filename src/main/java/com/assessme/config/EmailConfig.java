package com.assessme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 11:28 AM
 */
@Component
public class EmailConfig {

    @Value("${email.host}")
    String host;

    @Value("${email.port}")
    int port;

    @Value("${email.username}")
    String username;

    @Value("${email.password}")
    String password;

    Properties props;

    public Properties getProps() {
        return props;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public EmailConfig() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

}
