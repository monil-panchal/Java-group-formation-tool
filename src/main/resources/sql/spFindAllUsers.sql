DROP PROCEDURE IF EXISTS spFindAllUsers;

CREATE PROCEDURE spFindAllUsers()
BEGIN
    SELECT u.user_id,
            u.banner_id,
            u.first_name,
            u.last_name,
            u.email,
            u.isActive
    FROM user AS u;
END;