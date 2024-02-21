package com.cat.repository;

import com.cat.model.dao.SessionReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionReviewRepository extends JpaRepository<SessionReview, UUID> {
    List<SessionReview> findAllByGps_Id(UUID gps);

}
