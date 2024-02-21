package com.cat.repository;

import com.cat.model.dao.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
    List<Goal> findAllByGps_Id(UUID id);
    @Query("SELECT g.id FROM Goal g WHERE g.gps.id = :gpsId")
    List<UUID> findAllGoalIdsByGpsId(@Param("gpsId") UUID gpsId);


}
