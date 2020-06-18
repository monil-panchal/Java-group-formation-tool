package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.assessme.db.CallStoredProcedure;

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

    private final ConnectionManager connectionManager;

    public CourseDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    // CourseDAO method for retrieving course by using courseCode
    @Override
    public Optional<Course> getCourseByCode(String courseCode) throws Exception {

        Optional<Course> course = Optional.empty();

        // SQL query for fetching the course record based on the courseCode
        CallStoredProcedure procedure = null;
        try {

            procedure = new CallStoredProcedure("spFindCourseByCode(?)");

            if ((!courseCode.isEmpty() && courseCode != null)) {

                procedure.setParameter(1,courseCode);
                ResultSet resultSet = procedure.getResultSet();

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
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }finally {
            if(procedure != null){
                procedure.finalSteps();
            }
        }
        //Closing the connection
//        dbConnectionBuilder.closeConnection(connection.get());
        return course;
    }

    
    // CourseDAO method for retrieving course by using courseName
    @Override
    public Optional<Course> getCourseByName(String courseName) throws Exception {

        Optional<Course> course = Optional.empty();

        // SQL query for fetching the course record based on the courseName
//        String selectQuery = "SELECT * FROM course WHERE course_name =\"" +  courseName+"\"";
        CallStoredProcedure procedure = null;

		try {
            // Getting the DB connection
//            connection = dbConnectionBuilder.createDBConnection();

            // Preparing the statement
//            Statement statement = connection.get().createStatement();
            procedure = new CallStoredProcedure("spFindCourseByName(?)");

            if ((!courseName.isEmpty() && courseName != null)) {

//                ResultSet resultSet = statement.executeQuery(selectQuery);
                procedure.setParameter(1, courseName);
                ResultSet resultSet = procedure.getResultSet();

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
        }finally {
            if(procedure != null){
                procedure.finalSteps();
            }
        }
        return course;
    }


    @Override
    public List<Course> getAllCourse() throws SQLException, ClassNotFoundException {

        // SQL query for fetching all the courses
        String selectCourseQuery = "SELECT * FROM course";

        CallStoredProcedure procedure = null;

        List<Course> courseList = new ArrayList<>();

        try {
            procedure = new CallStoredProcedure("spAllCourses()");
            ResultSet resultSet = procedure.getResultSet();

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
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (procedure != null) {
                procedure.finalSteps();
            }
        }

        return courseList;
    }

    @Override
    public Optional<Course> addCourse(Course course) throws Exception {
        //returns true if course added else returns false
        
    	Optional<Course> newCourse = Optional.empty();
    	CallStoredProcedure procedure = null;
    	
        try {
            procedure = new CallStoredProcedure("spAddCourse(?,?)");
            procedure.setParameter(1, course.getCourseCode());
            procedure.setParameter(2, course.getCourseName());
            int row = procedure.executeUpdate();
            logger.info(String.format("%d rows updated", row));

            // check if the record was inserted successfully
            if (row > 0) {
                String successString = String.format("Course Inserted Succeccfully \nCourse Code:%s\nCourse Name:%s", course.getCourseCode(),course.getCourseName());
                logger.info(successString);

            } else {
                String failureString = String.format("Course Inserted Failed \nCourse Code:%s\nCourse Name:%s", course.getCourseCode(),course.getCourseName());
                logger.error(failureString);
                throw new Exception(failureString);
            }

            return newCourse;

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if(procedure != null){
                procedure.finalSteps();
            }
        }

    }

    @Override
    public Boolean removeCourseByCourseCode(String courseCode) throws Exception{
        //returns true if course added else returns false
        CallStoredProcedure procedure = null;
    	
        try {
            procedure = new CallStoredProcedure( "spRemoveCourseByCode(?)");
            procedure.setParameter(1, courseCode);

            int row = procedure.executeUpdate();
            
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
        finally {
            if(procedure != null){
                procedure.finalSteps();
            }
        }
    }

    @Override
    public Boolean removeCourseByCourseName(String courseName) throws Exception{
        //returns true if course added else returns false
        CallStoredProcedure procedure = null;
        try {
            procedure = new CallStoredProcedure( "spDeleteCourseByName(?)");
            procedure.setParameter(1, courseName);
            int row = procedure.executeUpdate();

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
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
    }

    /*
    @Override
    public Optional<Course> updateCourse(Course course) throws Exception {
        return Optional.empty();
    }*/

    @Override
    public Optional<List<Course>> listCourseByUserAndRole(long user_id, int roleId) throws Exception {

        List<Course> courseList = new ArrayList<>();

        CallStoredProcedure procedure = null;
        try{
            // Calling Procedure
            procedure = new CallStoredProcedure("spFindCourseByUserAndRole(?,?)");

            // Setting Query parameters
            procedure.setParameter(1, user_id);
            procedure.setParameter(2,roleId);

            // Obtain Result Set
            ResultSet resultSet = procedure.getResultSet();
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
        } finally {
            //Closing the connection
            if (procedure != null) {
                procedure.finalSteps();
            }
        }
    }
}
