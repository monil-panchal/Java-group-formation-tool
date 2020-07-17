package com.assessme.config;

import java.util.Properties;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 11:28 AM
 */
public interface EmailConfig {

    Properties getProps();

    String getHost();

    int getPort();

    String getUsername();

    String getPassword();
}
