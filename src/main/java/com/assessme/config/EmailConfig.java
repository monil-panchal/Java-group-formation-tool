package com.assessme.config;

import org.springframework.mail.SimpleMailMessage;

import java.util.Properties;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 11:28 AM
 */
public class EmailConfig {
    String host = "smtp.mailtrap.io";
    int port = 2525;
    String username = "6612d1ce0bdec9";
    String password = "432f71435e7ae5";
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
