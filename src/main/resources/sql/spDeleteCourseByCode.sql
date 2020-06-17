DROP PROCEDURE IF EXISTS spDeleteCourseByCode;

CREATE PROCEDURE spDeleteCourseByCode(
 IN courseCode VARCHAR(64)
)
BEGIN
    DELETE
    FROM course
    WHERE
        course_code = courseCode;
END;