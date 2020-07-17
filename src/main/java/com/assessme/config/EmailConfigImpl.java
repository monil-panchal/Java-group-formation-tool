package com.assessme.config;

import java.util.Properties;

/**
 * @author Darshan Kathiriya
 * @created 13-June-2020 8:47 PM
 */
public class EmailConfigImpl implements EmailConfig {

    String HOST = System.getenv("EMAIL_HOST");
    int PORT = 2525;
    String USERNAME = System.getenv("EMAIL_USERNAME");
    String PASSWORD = System.getenv("EMAIL_PASSWORD");
    Properties props;

    public EmailConfigImpl() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

    public Properties getProps() {
        return props;
    }

    public String getHost() {
        return HOST;
    }

    public int getPort() {
        return PORT;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

}
