package com.cat.controller;

import com.cat.model.dao.Supervisor;
import com.cat.model.dto.Employee.CreateEmployeeDTO;
import com.cat.model.dto.Employee.EmployeeWithSupervisorsDTO;
import com.cat.model.dto.supervisor.SupervisorDTO;
import com.cat.service.ManagerService;
import com.cat.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supervisors")
public class SupervisorController {
    private final SupervisorService supervisorService;
    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<Page<SupervisorDTO>> getAllSupervisors(Pageable pageable) {
        return ResponseEntity.ok().body(supervisorService.getAllSupervisors(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupervisorDTO> getSupervisorById(@PathVariable(value = "id") UUID supervisorId) {
        SupervisorDTO supervisor = supervisorService.getSupervisorById(supervisorId);
        return ResponseEntity.ok().body(supervisor);
    }

    @PostMapping
    public Supervisor createSupervisor(@RequestBody CreateEmployeeDTO supervisor) {
        return supervisorService.createSupervisor(supervisor);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Supervisor> updateSupervisor(@PathVariable(value = "id") UUID supervisorId,
                                                       @RequestBody Supervisor supervisorDetails) {
        final Supervisor updatedSupervisor = supervisorService.updateSupervisor(supervisorId, supervisorDetails);
        return ResponseEntity.ok(updatedSupervisor);
    }
    @PatchMapping("/add/subordinate/{supervisorId}/{employeeId}")
    public SupervisorDTO addSubordinate(@PathVariable(value = "supervisorId") UUID supervisorId, @PathVariable(value = "employeeId") UUID employeeId){
        return this.supervisorService.addSubordinate(employeeId,  supervisorId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Supervisor> deleteSupervisor(@PathVariable(value = "id") UUID supervisorId) {
        return supervisorService.deleteSupervisorById(supervisorId);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeWithSupervisorsDTO>> getEmployees(@PathVariable(value = "id") UUID supervisorId) {
        List<EmployeeWithSupervisorsDTO> employees = supervisorService.getEmployees(supervisorId);
        return ResponseEntity.ok().body(employees);
    }
}
