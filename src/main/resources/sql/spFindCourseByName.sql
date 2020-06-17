DROP PROCEDURE IF EXISTS spFindCourseByName;

CREATE PROCEDURE spFindCourseByName(
 IN courseName VARCHAR(64)
)
BEGIN
    SELECT course_code, course_name
    FROM course
    WHERE
        course_name = courseName;
END;