DROP PROCEDURE IF EXISTS spGetUserWithRolesFromEmail;

CREATE PROCEDURE spGetUserWithRolesFromEmail(
    IN mail VARCHAR (255)
)
BEGIN
    SELECT u.banner_id, u.first_name, u.last_name, u.email, u.isActive, u.password, r.role_name
    FROM user AS u
    INNER JOIN user_role AS ur
    ON u.user_id = ur.user_id
    INNER JOIN role AS r
    ON ur.role_id = r.role_id
    WHERE u.email = mail;
END;