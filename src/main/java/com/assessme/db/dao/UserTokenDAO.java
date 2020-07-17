package com.assessme.db.dao;

/**
 * @author: monil Created on: 2020-06-04
 */

import com.assessme.model.UserToken;
import java.util.Optional;

/**
 * The implementation of this class provides CRUD operations for the user_token table of the
 * database.
 */

public interface UserTokenDAO {

    Optional<UserToken> addUserToken(UserToken userToken) throws Exception;

    Optional<UserToken> getUserToken(Long userId) throws Exception;

}
