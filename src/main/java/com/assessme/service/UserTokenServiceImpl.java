package com.assessme.service;

import com.assessme.db.dao.UserTokenDAOImpl;
import com.assessme.model.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: monil
 * Created on: 2020-06-04
 */

/**
 * UserToken service layer class for this application
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {

    private Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);

    private UserTokenDAOImpl userTokenDAOImpl;

    public UserTokenServiceImpl(UserTokenDAOImpl userTokenDAOImpl) {
        this.userTokenDAOImpl = userTokenDAOImpl;
    }

    @Override
    public Optional<UserToken> addUserToken(UserToken userToken) throws Exception {
        Optional<UserToken> newUserToken = Optional.empty();
        try {
            newUserToken = userTokenDAOImpl.addUserToken(userToken);
            if (newUserToken.isPresent()) {
                //  newUser = Optional.of(user);
                String resMessage = String.format("User token with email: %s has been successfully added to the user table", userToken.getUserId());
                logger.info(resMessage);
            } else {
                throw new Exception(String.format("Error in creating a user token with email: %s", userToken.getUserId()));
            }

        } catch (Exception e) {
            String errMessage = String.format("Error in adding the user token to the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return newUserToken;
    }

    @Override
    public Optional<UserToken> getUserToken(Long userId) throws Exception {
        Optional<UserToken> userToken;
        try {
            userToken = userTokenDAOImpl.getUserToken(userId);
            String resMessage = String.format("UserToken with id: %s has been retrieved from the database", userId);
            logger.info(resMessage);
        } catch (Exception e) {
            String errMessage = String.format("Error in retrieving the user token from the database");
            logger.error(errMessage);
            e.printStackTrace();
            throw e;
        }
        return userToken;
    }
}
