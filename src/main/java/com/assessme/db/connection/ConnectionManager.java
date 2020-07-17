package com.assessme.db.connection;


import com.assessme.SystemConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

    private static ConnectionManager uniqueInstance = null;
    private final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private final String dbURL;
    private final String dbUserName;
    private final String dbPassword;
    private final String dbName;
    private final int dbPort;

    public ConnectionManager() {
        IDatabaseConfiguration config = SystemConfig.getInstance().getDatabaseConfiguration();
        dbURL = config.getDatabaseURL();
        dbUserName = config.getDatabaseUserName();
        dbPassword = config.getDatabasePassword();
        dbName = config.getDatabaseName();
        dbPort = config.getDatabasePort();
    }

    public static ConnectionManager getInstance() {
        if (null == uniqueInstance) {
            uniqueInstance = new ConnectionManager();
        }
        return uniqueInstance;
    }

    public Optional<Connection> getDBConnection() throws Exception {
        Optional<Connection> connection = Optional.empty();
        try {
            String databaseUrl = String.format(
                "jdbc:mysql://%s:%d/%s?%s",
                dbURL, dbPort, dbName,
                "useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC");
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = Optional
                .of(DriverManager.getConnection(databaseUrl, dbUserName, dbPassword));
            logger.info("Connection to the database established successfully.");

            return connection;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in creating the database connection");
            throw e;
        }
    }

    public boolean closeConnection(Connection connection) throws SQLException {
        connection.close();
        return true;
    }
}
