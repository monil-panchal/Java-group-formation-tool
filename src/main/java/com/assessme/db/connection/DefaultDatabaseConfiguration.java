package com.assessme.db.connection;

public class DefaultDatabaseConfiguration implements IDatabaseConfiguration {
    private static final String URL = "db-5308.cs.dal.ca";
    private static final String DB_NAME = "CSCI5308_13_DEVINT";
    private static final String USER = "CSCI5308_13_DEVINT_USER";
    private static final String PASSWORD = "CSCI5308_13_DEVINT_13329";
    private static final int PORT = 3306;

    public String getDatabaseUserName() {
        return USER;
    }

    public String getDatabasePassword() {
        return PASSWORD;
    }

    public String getDatabaseURL() {
        return URL;
    }

    public String getDatabaseName() {
        return DB_NAME;
    }

    public int getDatabasePort(){return PORT;}
}
