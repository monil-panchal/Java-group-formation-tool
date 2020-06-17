DROP PROCEDURE IF EXISTS spAddUserCourseRole;

CREATE PROCEDURE spAddUserCourseRole(
    IN userID BIGINT(20),
    IN courseID BIGINT(20),
    IN roleId INT(11)
)
BEGIN
    INSERT INTO
    user_course_role(user_id,
                    course_id,
                    role_id)
    VALUES (userId,
            courseId,
            roleId);
END;