package com.assessme.service;

import com.assessme.model.UserPasswordHistory;
import java.util.List;
import java.util.Optional;

/**
 * @author: monil Created on: 2020-06-16
 */
public interface UserPasswordHistoryService {

    List<UserPasswordHistory> getUserPasswordHistory(Long userId, Integer lastPasswords);

    Optional<UserPasswordHistory> addUserPasswordRecord(UserPasswordHistory userPasswordHistory)
        throws Exception;
}
