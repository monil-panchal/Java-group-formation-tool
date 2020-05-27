package com.assessme.db.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-27
 */

/**
 * Utility class for creating a connection to the Database using JDBC.
 */
@Component
public class DBConnectionBuilder {
    private Logger logger = LoggerFactory.getLogger(DBConnectionBuilder.class);

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private String port;

    @Value("${db.database}")
    private String database;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    /**
     * Creating a connection to the database.
     */
    public Optional<Connection> createDBConnection() throws SQLException, ClassNotFoundException {

        Optional<Connection> connection = Optional.empty();
        try {
            String databaseUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = Optional.of(DriverManager.getConnection(databaseUrl, username, password));
            logger.info("Connection to the database established successfully.");

            return connection;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in creating the database connection connection");
            throw e;
        }
    }
}
