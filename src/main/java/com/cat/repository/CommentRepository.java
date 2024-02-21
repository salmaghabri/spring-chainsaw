package com.cat.repository;

import com.cat.model.dao.Comment;
import com.cat.model.dao.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findCommentsByGoal_Id(UUID goalId);
    int countCommentByGoal_Id(UUID goalId );
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.goal.id = :goalId")
    void deleteCommentsByGoalId(@Param("goalId") UUID goalId);
    void deleteAllByGoal(Goal goal);
}
