package com.assessme.db.dao;

import com.assessme.db.connection.DBConnectionBuilder;
import com.assessme.model.Course;
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
        String selectQuery = "SELECT * FROM course WHERE course_code =" + courseCode;

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
                    course.get().setCourseId(resultSet.getInt("course_code"));
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
                course.setCourseId(resultSet.getInt("course_code"));
                course.setCourseName(resultSet.getString("course_name"));

                // Adding course to the list
                courseList.add(course);
            }

            //Closing the connection
            dbConnectionBuilder.closeConnection(connection.get());

            logger.info(String.format("Course list retrieved from the database: %s", courseList));

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return courseList;
    }

    @Override
    public Boolean addCourse(Course course) throws Exception {
        return null;
    }

    @Override
    public Boolean removeCourse(Course course) throws Exception {
        return null;
    }
    /*
    @Override
    public Optional<Course> updateCourse(Course course) throws Exception {
        return Optional.empty();
    }*/
}
