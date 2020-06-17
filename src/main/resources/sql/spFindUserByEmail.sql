DROP PROCEDURE IF EXISTS spFindUserByEmail;

CREATE PROCEDURE spFindUserByEmail(
    IN eMail VARCHAR(255)
)
BEGIN
    SELECT u.user_id,
            u.banner_id,
            u.first_name,
            u.last_name,
            u.email,
            u.isActive
    FROM user AS u
    WHERE
        u.email = eMail;
END;