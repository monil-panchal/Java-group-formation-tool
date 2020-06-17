DROP PROCEDURE IF EXISTS spAddUserToken;

CREATE PROCEDURE spAddUserToken(
    IN userID BIGINT(20),
    IN token VARCHAR(255)
)
BEGIN
    INSERT INTO
    user_token(user_id,
                token)
    VALUES (userId,
            token);
END;