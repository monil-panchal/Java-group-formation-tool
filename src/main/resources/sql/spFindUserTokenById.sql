DROP PROCEDURE IF EXISTS spFindUserTokenById;

CREATE PROCEDURE spFindUserTokenById(
 IN userId bigint(20)
)
BEGIN
    SELECT user_id, token
    FROM user_token
    WHERE
        user_id = userId;
END;