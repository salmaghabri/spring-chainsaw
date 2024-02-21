package com.cat.controller;

import com.cat.model.dao.Employee;
import com.cat.model.dto.Employee.CreateEmployeeDTO;
import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(Pageable pageable) {
        return ResponseEntity.ok().body(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") UUID employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody CreateEmployeeDTO employee) {
        System.out.println(employee);
        return employeeService.createEmployee(employee);
    }



    @PatchMapping ("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") UUID employeeId,
                                                   @RequestBody Employee employeeDetails) {
        final Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") UUID employeeId) {
        return employeeService.deleteEmployeeById(employeeId);

    }

    @GetMapping("/supervisors/{id}")
    public ResponseEntity<List<EmployeeDTO>> getSupervisorsByEmployee(@PathVariable(value = "id") UUID employeeId) {
        List<EmployeeDTO> supervisors = employeeService.getAllSupervisors(employeeId);
        return ResponseEntity.ok().body(supervisors);
    }

}
