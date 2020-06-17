DROP PROCEDURE if EXISTS spUpdatePasswordWithEmail;

CREATE PROCEDURE spUpdatePasswordWithEmail(
    IN mail VARCHAR (255),
    IN pass VARCHAR (255)
)
BEGIN
    UPDATE user AS u
    SET password = pass
    WHERE u.email = mail;
END;