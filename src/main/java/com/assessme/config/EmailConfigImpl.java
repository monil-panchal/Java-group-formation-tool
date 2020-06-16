package com.assessme.config;

import java.util.Properties;

/**
 * @author Darshan Kathiriya
 * @created 13-June-2020 8:47 PM
 */
public class EmailConfigImpl implements EmailConfig {

  String host = "smtp.mailtrap.io";
  int port = 2525;
  String username = "6612d1ce0bdec9";
  String password = "432f71435e7ae5";
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

}
