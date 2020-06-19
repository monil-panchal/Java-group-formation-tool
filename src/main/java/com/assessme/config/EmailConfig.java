package com.assessme.config;

import java.util.Properties;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 11:28 AM
 */
public interface EmailConfig {

  public Properties getProps();

  public String getHost();

  public int getPort();

  public String getUsername();

  public String getPassword();
}
