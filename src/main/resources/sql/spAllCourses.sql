DROP PROCEDURE IF EXISTS spAllCourses;

CREATE PROCEDURE spAllCourses ()
BEGIN
    SELECT course_code, course_name
    FROM course;
END;