package com.assessme.db.connection;

public interface IDatabaseConfiguration {

    String getDatabaseUserName();

    String getDatabasePassword();

    String getDatabaseURL();

    String getDatabaseName();

    int getDatabasePort();
}
