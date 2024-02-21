package com.cat.repository;

import com.cat.model.dao.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {
}
