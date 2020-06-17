DROP PROCEDURE if EXISTS getUserNotAssignedForCourse;

CREATE PROCEDURE getUserNotAssignedForCourse(
    IN courseId bigint (20),
    IN roleId int (11)
)
BEGIN
    SELECT u.user_id,u.banner_id,u.first_name,u.last_name,u.email,u.password, u.isActive
    FROM user u
    JOIN user_course_role e
    ON u.user_id = e.user_id
    WHERE e.course_id != courseId
    AND role_id != roleId;
END;