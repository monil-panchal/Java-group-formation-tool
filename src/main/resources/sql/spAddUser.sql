DROP PROCEDURE if EXISTS spAddUser;

CREATE PROCEDURE spAddUser(
    IN bannerID VARCHAR(255),
    IN firstName VARCHAR (255),
    IN lastName VARCHAR (255),
    IN eMail VARCHAR (255),
    IN pass VARCHAR (255),
    IN isactive TINYINT (1)
)
BEGIN
    INSERT INTO user
                (banner_id,
                 first_name,
                 last_name,
                 email,
                 password,
                 isActive)
    VALUES (bannerID,
            firstName,
            lastName,
            eMail,
            pass,
            isactive);
END;