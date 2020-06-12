package com.assessme.db.connection;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-05-27
 */

@SpringBootTest
public class DBConnectionBuilderTest {

    @MockBean
    private DBConnectionBuilder mockDbConnectionBuilder;

    @Mock
    private Connection mockConnection;

    @Mock
    private Connection connection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @Test
    public void createDBConnectionTest() throws SQLException, ClassNotFoundException {

        System.out.println("Checking the application database connectivity");

        Mockito.when(mockDbConnectionBuilder.createDBConnection())
                .thenReturn(Optional.of(connection));

    }

    @Test
    public void JDBCConfigurationTest() throws SQLException {

        System.out.println("Running JDBC configuration test");

        Mockito.when(mockConnection.createStatement())
                .thenReturn(mockStatement);

        Mockito.when(mockConnection.createStatement()
                .executeQuery(Mockito.anyString()))
                .thenReturn(mockResultSet);
    }
}