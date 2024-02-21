package com.cat.repository;

import com.cat.model.dao.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,UUID> {
}
