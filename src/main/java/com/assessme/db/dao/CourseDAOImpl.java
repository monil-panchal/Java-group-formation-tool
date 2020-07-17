package com.assessme.db.dao;

import com.assessme.db.connection.ConnectionManager;
import com.assessme.model.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: hardik Created on: 2020-05-30
 */

public class CourseDAOImpl implements CourseDAO {

    private static CourseDAOImpl instance;
    private final Logger logger = LoggerFactory.getLogger(CourseDAOImpl.class);
    private final ConnectionManager connectionManager;

    public CourseDAOImpl() {
        connectionManager = new ConnectionManager();
    }

    public static CourseDAOImpl getInstance() {
        if (instance == null) {
            instance = new CourseDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<Course> getCourseByCode(String courseCode) throws Exception {
        Optional<Course> course = Optional.empty();
        String selectQuery = "SELECT * FROM course WHERE course_code =\"" + courseCode + "\"";
        try (
                Connection connection = connectionManager.getDBConnection().get();
                Statement statement = connection.createStatement()
        ) {
            if (!courseCode.isEmpty()) {
                ResultSet resultSet = statement.executeQuery(selectQuery);
                if (!resultSet.isBeforeFirst()) {
                    logger.error(
                            String.format("Course: %s is not found in the database", courseCode));
                    throw new Exception(
                            String.format("Course: %s is not found in the database", courseCode));
                }
                logger.info("Course data retrieved successfully");
                while (resultSet.next()) {
                    course = Optional.of(new Course());
                    course.get().setCourseId(resultSet.getLong("course_id"));
                    course.get().setCourseCode(resultSet.getString("course_code"));
                    course.get().setCourseName(resultSet.getString("course_name"));
                }
                String successString = String
                        .format("Course with course code: %s retrieved successfully.", courseCode);
                logger.info(successString);

            } else {
                throw new Exception(
                        String.format("Course: %s record is not found in the Database.", courseCode));
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return course;
    }


    @Override
    public Optional<Course> getCourseByName(String courseName) throws Exception {
        Optional<Course> course = Optional.empty();
        String selectQuery = "SELECT * FROM course WHERE course_name =\"" + courseName + "\"";
        try (
                Connection connection = connectionManager.getDBConnection().get();
                Statement statement = connection.createStatement()
        ) {
            if (!courseName.isEmpty()) {
                ResultSet resultSet = statement.executeQuery(selectQuery);
                if (!resultSet.isBeforeFirst()) {
                    logger.error(
                            String.format("Course: %s is not found in the database", courseName));
                    throw new Exception(
                            String.format("Course: %s is not found in the database", courseName));
                }
                logger.info("Course data retrieved successfully");
                while (resultSet.next()) {
                    course = Optional.of(new Course());
                    course.get().setCourseId(resultSet.getLong("course_id"));
                    course.get().setCourseCode(resultSet.getString("course_code"));
                    course.get().setCourseName(resultSet.getString("course_name"));
                }
                String successString = String
                        .format("Course with course name: %s retrieved successfully.", courseName);
                logger.info(successString);
            } else {
                throw new Exception(
                        String.format("Course: %s record is not found in the Database.", courseName));
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return course;
    }


    @Override
    public List<Course> getAllCourse() throws Exception {
        List<Course> courseList = new ArrayList<>();
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT * FROM course")
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Course course = new Course();

                course.setCourseId(resultSet.getLong("course_id"));
                course.setCourseCode(resultSet.getString("course_code"));
                course.setCourseName(resultSet.getString("course_name"));

                courseList.add(course);
            }
            logger.info(String.format("Course list retrieved from the database: %s", courseList));

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
        return courseList;
    }

    @Override
    public Optional<Course> addCourse(Course course) throws Exception {
        Optional<Course> newCourse = Optional.empty();
        String insertCourseQuery = "INSERT INTO course(course_code, course_name) VALUES(?, ?)";
        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(insertCourseQuery) // create a statement

        ) {
            preparedStatement.setString(1, course.getCourseCode());
            preparedStatement.setString(2, course.getCourseName());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                String successString = String
                        .format("Course Inserted Succeccfully \nCourse Code:%s\nCourse Name:%s",
                                course.getCourseCode(), course.getCourseName());
                logger.info(successString);

            } else {
                String failureString = String
                        .format("Course Inserted Failed \nCourse Code:%s\nCourse Name:%s",
                                course.getCourseCode(), course.getCourseName());
                logger.error(failureString);
                throw new Exception(failureString);
            }
            return newCourse;

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean removeCourseByCourseCode(String courseCode) throws Exception {

        String SQL_DELETE = "DELETE FROM course WHERE course_code=?";

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQL_DELETE) // create a statement
        ) {

            preparedStatement.setString(1, courseCode);
            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                logger.info(
                        String.format("Deletion Successfull! Course with course code: %s", courseCode));
                return true;
            } else {
                logger.info(
                        String.format("Deletion Failed! Course with course code: %s", courseCode));
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean removeCourseByCourseName(String courseName) throws Exception {

        String SQL_DELETE = "DELETE FROM course WHERE course_name=?";

        try (
                Connection connection = connectionManager.getDBConnection().get();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQL_DELETE)
        ) {

            preparedStatement.setString(1, courseName);
            int row = preparedStatement.executeUpdate();

            if (row == 1) { //one row deleted
                logger.info(
                        String.format("Deletion Successfull! Course with course name: %s", courseName));
                return true;
            } else {
                logger.info(
                        String.format("Deletion Failed! Course with course name: %s", courseName));
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<List<Course>> listCourseByUserAndRole(long user_id, int roleId)
            throws Exception {
        String sqlQuery =
                "SELECT c.course_id, c.course_code, c.course_name FROM user_course_role e" +
                        " JOIN course c ON e.course_id = c.course_id WHERE e.user_id = ? AND e.role_id=?";
        List<Course> courseList = new ArrayList<>();
        try (
                Connection dbConnection = connectionManager.getDBConnection().get();
                PreparedStatement stmt = dbConnection.prepareStatement(sqlQuery)
        ) {
            stmt.setLong(1, user_id);
            stmt.setInt(2, roleId);
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
