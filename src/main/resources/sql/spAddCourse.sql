DROP PROCEDURE if EXISTS spAddCourse;

CREATE PROCEDURE spAddCourse(
    IN courseCode VARCHAR(64),
    IN courseName VARCHAR (64)
)
BEGIN
    INSERT INTO course
                (course_code,
                course_name)
    VALUES (courseCode,
            courseName);
END;