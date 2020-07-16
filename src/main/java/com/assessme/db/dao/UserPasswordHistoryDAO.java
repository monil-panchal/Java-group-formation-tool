package com.assessme.db.dao;

/**
 * @author: monil Created on: 2020-06-16
 */

import com.assessme.model.UserPasswordHistory;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of this class provides CRUD operations for the user_password_history table of
 * the database.
 */
public interface UserPasswordHistoryDAO {

    Optional<UserPasswordHistory> addPasswordModificationRecord(
        UserPasswordHistory userPasswordHistory) throws Exception;

    List<UserPasswordHistory> getUserPasswordHistory(Long userId, Integer lastPasswords)
        throws Exception;

}
