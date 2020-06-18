package com.assessme.db;

import com.assessme.db.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CallStoredProcedure {

    private Logger logger = LoggerFactory.getLogger(CallStoredProcedure.class);

    private ConnectionManager connectionManager;
    private String procedureName;
    private Connection connection;
    private CallableStatement callableStatement;

    public CallStoredProcedure(String procedureName) throws SQLException, ClassNotFoundException{
        this.procedureName = procedureName;
//        connection = dbConnectionBuilder.createDBConnection();
        connection = new ConnectionManager().getDBConnection().get();
        callableStatement = connection.prepareCall("{call " +  procedureName + "}");
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
                if(connection.isClosed()){
                    connection.close();
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
