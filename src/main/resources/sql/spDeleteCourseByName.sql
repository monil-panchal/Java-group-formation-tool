DROP PROCEDURE IF EXISTS spDeleteCourseByName;

CREATE PROCEDURE spDeleteCourseByName(
 IN courseName VARCHAR(64)
)
BEGIN
    DELETE
    FROM course
    WHERE
        course_name = courseName;
END;