DROP PROCEDURE IF EXISTS spAddUserRole;

CREATE PROCEDURE spAddUserRole(
 IN userId bigint(20),
 IN roleId int(15)
)
BEGIN
    INSERT INTO user_role(user_id,
                            role_id)
    VALUES (userId,
            roleId)
    ON DUPLICATE KEY UPDATE user_id=LAST_INSERT_ID(userId);
END;