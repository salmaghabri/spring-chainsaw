package com.cat.repository;

import com.cat.model.dao.Goal;
import com.cat.model.dao.GoalReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GoalReviewRepository extends JpaRepository<GoalReview, UUID> {

    List<GoalReview> findAllByGoal_Id(UUID goalId);
    List<GoalReview> findAllByGoal_IdAndSessionReview_Id(UUID goalId, UUID sessionReviewId);
    GoalReview findByGoal_IdAndCreatedByAndSessionReview_Id(UUID goalId, UUID employeeId,UUID sessionReviewId);
    @Query("SELECT g.sessionReview.id AS sessionId, g FROM GoalReview g WHERE g.goal.id = :goalId GROUP BY g.sessionReview.id")
    List<Map<UUID, List<GoalReview>>> findGoalReviewsGroupedBySessionReviews(@Param("goalId") UUID goalId);
    void deleteAllByGoal(Goal goal);

}
