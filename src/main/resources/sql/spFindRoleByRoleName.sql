DROP PROCEDURE IF EXISTS spFindRoleByRoleName;

CREATE PROCEDURE spFindRoleByRoleName(
 IN roleName VARCHAR(255)
)
BEGIN
    SELECT role_id, role_name
    FROM role
    WHERE
        role_name = roleName;
END;