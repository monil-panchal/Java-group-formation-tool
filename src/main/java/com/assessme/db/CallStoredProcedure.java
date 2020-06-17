package com.assessme.db;

import com.assessme.db.connection.DBConnectionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class CallStoredProcedure {

    private Logger logger = LoggerFactory.getLogger(DBConnectionBuilder.class);

    private DBConnectionBuilder dbConnectionBuilder;
    private String procedureName;
    private Optional<Connection> connection;
    private CallableStatement callableStatement;

    public CallStoredProcedure(DBConnectionBuilder dbConnectionBuilder, String procedureName) throws SQLException, ClassNotFoundException{
        this.procedureName = procedureName;
        this.dbConnectionBuilder = dbConnectionBuilder;
        connection = dbConnectionBuilder.createDBConnection();
        callableStatement = connection.get().prepareCall("{call " +  procedureName + "}");
    }

    public void setParameter(int index, String value) throws SQLException{
        callableStatement.setString(index, value);
    }

    public void setParameter(int index, Long value) throws SQLException{
        callableStatement.setLong(index, value);
    }

    public void setParameter(int index, int value) throws SQLException{
        callableStatement.setInt(index, value);
    }

    public void setParameter(int index, boolean value) throws SQLException{
        callableStatement.setBoolean(index, value);
    }

    public ResultSet getResultSet() throws SQLException{
        if(callableStatement.execute()){
            return callableStatement.getResultSet();
        }
        else{
            return null;
        }
    }

    public void execute() throws SQLException
    {
        callableStatement.execute();
    }

    public int executeUpdate() throws SQLException
    {
         return callableStatement.executeUpdate();
    }

    public ResultSet getGeneratedKeys() throws SQLException{
        return callableStatement.getGeneratedKeys();
    }

    public void finalSteps(){
        try{
            if(callableStatement != null){
                callableStatement.close();
            }

            if(connection != null){
                if(connection.get().isClosed()){
                    connection.get().close();
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
