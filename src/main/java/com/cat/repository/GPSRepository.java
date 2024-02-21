package com.cat.repository;

import com.cat.model.dao.GPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository

public interface GPSRepository extends JpaRepository<GPS, UUID> {
    Optional<List<GPS>> findAllByEmployee_Id(UUID id);
    @Query("SELECT gps.id FROM GPS gps WHERE gps.employee.id = :employeeId")
    List<UUID> getAllGPSIdsByEmployee(UUID employeeId);



}
