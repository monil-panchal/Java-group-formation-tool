DROP PROCEDURE IF EXISTS spFindCourseByUserAndRole;

CREATE PROCEDURE spFindCourseByUserAndRole(
    IN userId bigint (20),
    IN roleId bigint (11)
)
BEGIN
    SELECT c.course_id, c.course_code, c.course_name
    FROM user_course_role e
    JOIN course c
        ON e.course_id = c.course_id
    WHERE e.user_id = userId
      AND e.role_id=roleId;
END;