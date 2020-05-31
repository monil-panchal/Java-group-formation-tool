package com.assessme.config;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 12:08 AM
 */
public class DatabaseConfig implements IDatabaseConfig {

    private final String host = "db-5308.cs.dal.ca";
    private final String port = "3306";
    private final String databse = "CSCI5308_13_DEVINT";
    private final String userName = "CSCI5308_13_DEVINT_USER";
    private final String password = "CSCI5308_13_DEVINT_13329";


    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDatabase() {
        return databse;
    }
}
