package com.assessme.db.connection;

public class DefaultDatabaseConfiguration implements IDatabaseConfiguration {

    private static final String URL = System.getenv("DB_URL");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
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

    public int getDatabasePort() {
        return PORT;
    }
}
