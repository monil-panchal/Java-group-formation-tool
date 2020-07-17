package com.assessme.service;

import com.assessme.model.UserToken;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-06-04
 */
public interface UserTokenService {

    Optional<UserToken> addUserToken(UserToken user) throws Exception;

    Optional<UserToken> getUserToken(Long userId) throws Exception;
}
