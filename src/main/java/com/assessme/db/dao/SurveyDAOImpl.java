//package com.assessme.db.dao;
//
//import com.assessme.db.connection.ConnectionManager;
//import com.assessme.model.Course;
//import com.assessme.model.Survey;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class SurveyDAOImpl implements SurveyDAO {
//
//    private static SurveyDAOImpl instance;
//    private final Logger logger = LoggerFactory.getLogger(SurveyDAOImpl.class);
//    private final ConnectionManager connectionManager;
//
//    public SurveyDAOImpl(){
//        connectionManager = new ConnectionManager();
//    }
//
//    public static SurveyDAOImpl getInstance() {
//        if (instance == null) {
//            instance = new SurveyDAOImpl();
//        }
//        return instance;
//    }
//
//    @Override
//    public List<Survey> getAllSurvey() throws Exception {
//        List<Survey> surveyList = new ArrayList<>();
//        Survey survey;
//        String selectQuery = "select * from survey";
//        int rows = 0;
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try{
//            connection = connectionManager.getDBConnection().get();
//            preparedStatement = connection.prepareStatement(selectQuery);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.last();
//            rows = resultSet.getRow();
//            logger.info(String.format("Survey list fetched from database, %d rows fetched", rows));
//            resultSet.beforeFirst();
//
//            while (resultSet.next()){
//                survey = new Survey();
//                survey.setSurveyId(resultSet.getLong("survey_id"));
//                survey.setCourseId(resultSet.getLong("course_id"));
//                survey.setSurveyName(resultSet.getString("survey_name"));
//                survey.setSurveyState(resultSet.getBoolean("survey_state"));
//
//                surveyList.add(survey);
//            }
//        }catch (Exception e){
//            logger.error(e.getLocalizedMessage());
//            e.printStackTrace();
//            throw e;
//        }finally {
//            if(connection != null) {
//                connectionManager.closeConnection(connection);
//            }
//            if(preparedStatement != null) {
//                preparedStatement.close();
//            }
//        }
//        return surveyList;
//    }
//
//    @Override
//    public Optional<Survey> addSurvey(Survey survey) throws Exception {
//        Optional<Survey> newSurvey = Optional.empty();
//        String insertQuery = "INSERT INTO survey(course_code, survey_name, survey_state) VALUES(?, ?, ?)";
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try{
//            connection = connectionManager.getDBConnection().get();
//            preparedStatement = connection.prepareStatement(insertQuery);
//            preparedStatement.setLong(1, survey.getCourseId());
//            preparedStatement.setString(2,survey.getSurveyName());
//            preparedStatement.setBoolean(3, false);
//
//            int row = preparedStatement.executeUpdate();
//
//            if (row > 0) {
//                String successString = String
//                        .format("Survey Inserted Successfully, Course Id:%d, Survey Name:%s",
//                                survey.getCourseId(), survey.getSurveyName());
//                logger.info(successString);
//
//            } else {
//                String failureString = String
//                        .format("Survey Insertion Failed, Course Id:%d, Survey Name:%s",
//                                survey.getCourseId(), survey.getSurveyName());
//                logger.error(failureString);
//                throw new Exception(failureString);
//            }
//        }catch (Exception e){
//            logger.error(e.getLocalizedMessage());
//            e.printStackTrace();
//            throw e;
//        }finally {
//            if(connection != null) {
//                connectionManager.closeConnection(connection);
//            }
//            if(preparedStatement != null) {
//                preparedStatement.close();
//            }
//        }
//        return newSurvey;
//    }
//
//    @Override
//    public Optional<Survey> getSurveyByCourse(long courseId) throws Exception {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        Optional<Survey> survey = Optional.empty();
//        String sqlQuery = "select * from survey where course_id = '" + courseId + "'";
//        int rowsFetched = 0;
//        try{
//            connection = connectionManager.getDBConnection().get();
//            preparedStatement = connection.prepareStatement(sqlQuery);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (!resultSet.isBeforeFirst()) {
//                logger.error(String.format("Survey for course: %s is not found in the database", courseId));
//                throw new Exception(String.format("Survey for course: %s is not found in the database", courseId));
//            }
//            while (resultSet.next()){
//                survey = Optional.of(new Survey());
//                survey.get().setSurveyId(resultSet.getLong("survey_id"));
//                survey.get().setCourseId(resultSet.getLong("course_id"));
//                survey.get().setSurveyName(resultSet.getString("survey_name"));
//                survey.get().setSurveyState(resultSet.getBoolean("survey_state"));
//            }
//            rowsFetched = resultSet.getRow();
//            logger.info(String.format("%d survey(s) found for course_id: %d",rowsFetched, courseId));
//        }catch (Exception e){
//            logger.error(e.getLocalizedMessage());
//            e.printStackTrace();
//            throw e;
//        }finally {
//            if(connection != null) {
//                connectionManager.closeConnection(connection);
//            }
//            if(preparedStatement != null) {
//                preparedStatement.close();
//            }
//        }
//        return survey;
//    }
//}
