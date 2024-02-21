package com.cat.repository;

import com.cat.model.dao.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
//    List<Goal> getGoalsByUserId(String userId);

    Optional<Employee> findByUsername(String username);
}
