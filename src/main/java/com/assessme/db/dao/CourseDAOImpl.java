package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.Course;
import com.assessme.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: hardik
 * Created on: 2020-05-30
 */

/**
 * The {@link CourseDAO} implementation class for performing CRUD operations for the course table of the database.
 */
@Repository
public class CourseDAOImpl implements CourseDAO {

    private Logger logger = LoggerFactory.getLogger(CourseDAOImpl.class);
    private Optional<Connection> connection;

    private DBConnectionBuilder dbConnectionBuilder;

    public CourseDAOImpl(DBConnectionBuilder dbConnectionBuilder) {
        this.dbConnectionBuilder = dbConnectionBuilder;
    }

    // CourseDAO method for retrieving course by using courseCode
    @Override
    public Optional<Course> getCourseByCode(String courseCode) throws Exception {

        Optional<Course> course = Optional.empty();

        // SQL query for fetching the course record based on the courseCode


        String selectQuery = "SELECT * FROM course WHERE course_code =\"" + courseCode + "\"";
        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            Statement statement = connection.get().createStatement();

            if ((!courseCode.isEmpty() && courseCode != null)) {

                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("Course: %s is not found in the database", courseCode));
                    throw new Exception(String.format("Course: %s is not found in the database", courseCode));
                }

                logger.info(String.format("Course data retrieved successfully"));
                while (resultSet.next()) {

                    course = Optional.of(new Course());
                    course.get().setCourseId(resultSet.getLong("course_id"));
                    course.get().setCourseCode(resultSet.getString("course_code"));
                    course.get().setCourseName(resultSet.getString("course_name"));
                }
                String successString = String.format("Course with course code: %s retrieved successfully.", courseCode);
                logger.info(successString);

            } else
                throw new Exception(String.format("Course: %s record is not found in the Database.", courseCode));

        } catch (Exception e) {
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());

            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        //Closing the connection
        dbConnectionBuilder.closeConnection(connection.get());
        return course;
    }

    
    // CourseDAO method for retrieving course by using courseName
    @Override
    public Optional<Course> getCourseByName(String courseName) throws Exception {

        Optional<Course> course = Optional.empty();

        // SQL query for fetching the course record based on the courseName
        String selectQuery = "SELECT * FROM course WHERE course_name =\"" +  courseName+"\"";

		try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            Statement statement = connection.get().createStatement();

            if ((!courseName.isEmpty() && courseName != null)) {

                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (!resultSet.isBeforeFirst()) {
                    logger.error(String.format("Course: %s is not found in the database", courseName));
                    throw new Exception(String.format("Course: %s is not found in the database", courseName));
                }

                logger.info(String.format("Course data retrieved successfully"));
                while (resultSet.next()) {

                    course = Optional.of(new Course());	
                    course.get().setCourseId(resultSet.getLong("course_id"));
                    course.get().setCourseCode(resultSet.getString("course_code"));
                    course.get().setCourseName(resultSet.getString("course_name"));
                }
                String successString = String.format("Course with course name: %s retrieved successfully.", courseName);
                logger.info(successString);

            } else
                throw new Exception(String.format("Course: %s record is not found in the Database.", courseName));

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return course;
    }


    @Override
    public List<Course> getAllCourse() throws SQLException, ClassNotFoundException {

        // SQL query for fetching all the courses
        String selectCourseQuery = "SELECT * FROM course";

        List<Course> courseList = new ArrayList<>();

        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            PreparedStatement preparedStatement = connection.get().prepareStatement("SELECT * FROM course");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                // Instantiating new course
                Course course = new Course();

                //Setting the attributes
                course.setCourseId(resultSet.getLong("course_id"));
                course.setCourseCode(resultSet.getString("course_code"));
                course.setCourseName(resultSet.getString("course_name"));

                // Adding course to the list
                courseList.add(course);
            }
            logger.info(String.format("Course list retrieved from the database: %s", courseList));

        } catch (Exception e) {
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());

            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        //Closing the connection
        dbConnectionBuilder.closeConnection(connection.get());

        return courseList;
    }

    @Override
    public Optional<Course> addCourse(Course course) throws Exception {
        //returns true if course added else returns false
        
    	Optional<Course> newCourse = Optional.empty();
    	
        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            String insertCourseQuery = "INSERT INTO course(course_code, course_name) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.get().prepareStatement(insertCourseQuery); // create a statement
            preparedStatement.setString(1, course.getCourseCode()); // set input parameter 1
            preparedStatement.setString(2, course.getCourseName()); // set input parameter 2
            
            // Executing the query to store the user record
            int row = preparedStatement.executeUpdate();

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("Course Inserted Succeccfully \nCourse Code:%s\nCourse Name:%s", course.getCourseCode(),course.getCourseName());
                logger.info(successString);

            } else {
                String failureString = String.format("Course Inserted Failed \nCourse Code:%s\nCourse Name:%s", course.getCourseCode(),course.getCourseName());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            return newCourse;

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public Boolean removeCourseByCourseCode(String courseCode) throws Exception{
        //returns true if course added else returns false
    	
        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            String SQL_DELETE = "DELETE FROM course WHERE course_code=?";

            PreparedStatement preparedStatement = connection.get().prepareStatement(SQL_DELETE); // create a statement
            preparedStatement.setString(1, courseCode); // set input parameter 2
            int row = preparedStatement.executeUpdate(); // execute insert statement
            
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            
            if (row==1){ //one row deleted
            	logger.info(String.format("Deletion Successfull! Course with course code: %s", courseCode));
            	return true;
            }
            else{
            	logger.info(String.format("Deletion Failed! Course with course code: %s", courseCode));
            	return false;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public Boolean removeCourseByCourseName(String courseName) throws Exception{
        //returns true if course added else returns false
        
        try {
            // Getting the DB connection
            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
            String SQL_DELETE = "DELETE FROM course WHERE course_name=?";

            PreparedStatement preparedStatement = connection.get().prepareStatement(SQL_DELETE); // create a statement
            preparedStatement.setString(1, courseName); // set input parameter 2
            int row = preparedStatement.executeUpdate(); // execute insert statement
            
            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());
            
            if (row==1){ //one row deleted
            	logger.info(String.format("Deletion Successfull! Course with course name: %s", courseName));
            	return true;
            }
            else{
            	logger.info(String.format("Deletion Failed! Course with course name: %s", courseName));
            	return false;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }

    }

    /*
    @Override
    public Optional<Course> updateCourse(Course course) throws Exception {
        return Optional.empty();
    }*/

    @Override
    public Optional<List<Course>> listCourseByUser(long user_id, int roleId) throws Exception {
        String sqlQuery = "SELECT c.course_id, c.course_code, c.course_name FROM user_course_role e" +
                " JOIN course c ON e.course_id = c.course_id WHERE e.user_id = ? AND e.role_id=?";
        List<Course> courseList = new ArrayList<>();
        try (
                Connection dbConnection = dbConnectionBuilder.createDBConnection().get();
                PreparedStatement stmt = dbConnection.prepareStatement(sqlQuery);
        ) {
            stmt.setLong(1, user_id);
            stmt.setInt(2,roleId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt(1));
                course.setCourseCode(resultSet.getString(2));
                course.setCourseName(resultSet.getString(3));
                logger.info(course.toString());
                courseList.add(course);
            }
            return Optional.of(courseList);
        } catch (Exception e) {
            logger.error("Error Fetching Courses");
            throw e;
        }
    }
}
