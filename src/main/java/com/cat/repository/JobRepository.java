package com.cat.repository;

import com.cat.model.dao.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository  extends JpaRepository<Job, UUID> {

    Optional<Job> findByName(String s);
}
