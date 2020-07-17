package com.assessme.db.dao;

import com.assessme.model.QuestionType;
import java.util.List;
import java.util.Optional;

/**
 * @author Darshan Kathiriya
 * @created 16-June-2020 11:49 AM
 */
public interface QuestionTypeDAO {

    Optional<List<QuestionType>> getAllQuestionTypes() throws Exception;
}
