package com.cat.repository;

import com.cat.model.dao.GPSTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface GPSTemplateRepository extends JpaRepository<GPSTemplate, UUID> {
}
