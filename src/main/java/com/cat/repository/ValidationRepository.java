package com.cat.repository;

import com.cat.enums.Status;
import com.cat.model.dao.Goal;
import com.cat.model.dao.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ValidationRepository extends JpaRepository<Validation, UUID> {
    List<Validation> findAllByGoal_IdAndSessionReviewIsNull(UUID goalId); //validations to change the goal status
    List<Validation> findAllBySessionReview_Id(UUID id);
    int countValidationByGoal_IdAndSessionReviewIsNull(UUID goalId);
    int countValidationBySessionReview_Id(UUID sessionId);
   Validation findBySessionReview_IdAndValidator(UUID sessionId, UUID validator);
    void deleteAllByGoal(Goal goal);
    Validation findByGoalIdAndValidatorAndSessionReviewIsNull(UUID goalId, UUID validator );//validations to change the goal status
    @Query("SELECT COUNT(v) FROM Validation v WHERE v.goal.id = :goalId AND  v.status = :status")
    int countValidatedValidationsByGoalId(UUID goalId, Status status);
    @Query("SELECT COUNT(v) FROM Validation v WHERE v.sessionReview.id = :sessionId AND  v.status = :status")
    int countValidatedValidationsBySessionReviewId(UUID sessionId, Status status);
    @Query("SELECT AVG(v.ranking) FROM Validation v WHERE v.sessionReview.id = :sessionReviewId")
    Double getAverageRankingBySessionReviewId(UUID sessionReviewId);
}
