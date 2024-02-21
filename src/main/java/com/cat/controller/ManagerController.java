package com.cat.controller;

import com.cat.service.ManagerService;
import com.cat.model.dao.Manager;
import com.cat.model.dto.Employee.CreateEmployeeDTO;
import com.cat.model.dto.Employee.EmployeeWithSupervisorsDTO;
import com.cat.model.dto.Manager.ManagerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
    private final ManagerService managerService;


    @PostMapping
    public Manager createManager(@RequestBody CreateEmployeeDTO manager) {
        return managerService.createManager(manager);
    }

    @PatchMapping("/add/subordinate/{managerId}/{employeeId}")
    public ManagerDTO addSubordinate(@PathVariable(value = "managerId") UUID managerId,
                                        @PathVariable(value = "employeeId") UUID employeeId) {
        return managerService.addSubordinate(managerId, employeeId);
    }



    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeWithSupervisorsDTO>> getEmployees(@PathVariable(value = "id") UUID managerId) {
        List<EmployeeWithSupervisorsDTO> employees = managerService.getEmployees(managerId);
        return ResponseEntity.ok().body(employees);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable(value = "id") UUID managerId) {
        return managerService.deleteManagerById(managerId);
    }
}
