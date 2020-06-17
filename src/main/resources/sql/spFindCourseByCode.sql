DROP PROCEDURE IF EXISTS spFindCourseByCode;

CREATE PROCEDURE spFindCourseByCode(
 IN courseCode VARCHAR(64)
)
BEGIN
    SELECT course_code, course_name
    FROM course
    WHERE
        course_code = courseCode;
END;