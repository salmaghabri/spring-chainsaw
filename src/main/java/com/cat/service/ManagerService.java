package com.cat.service;


import com.cat.model.dao.Employee;
import com.cat.model.dao.Job;
import com.cat.model.dao.Manager;
import com.cat.model.dto.Employee.CreateEmployeeDTO;
import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.Employee.EmployeeWithSupervisorsDTO;
import com.cat.model.dto.Manager.ManagerDTO;
import com.cat.repository.EmployeeRepository;
import com.cat.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final JobService jobService;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public Page<ManagerDTO> getAllManagers(Pageable pageable) {
        Page<Manager> managersPage = managerRepository.findAll(pageable);
        List<ManagerDTO> managerDTOList = managersPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(managerDTOList, pageable, managersPage.getTotalElements());
    }
    public Manager createManager(CreateEmployeeDTO createManagerDTO) {
        Manager manager = new Manager();
        manager.setUsername(createManagerDTO.getUsername());
        manager.setEmail(createManagerDTO.getEmail());
        manager.setHierarchy(createManagerDTO.getHierarchy());
        manager.setProfilePictureURL(createManagerDTO.getProfilePictureURL());

        Job job = jobService.getJobByName(createManagerDTO.getJobName());
        manager.setJob(job);

        return managerRepository.save(manager);
    }
    public List<EmployeeWithSupervisorsDTO> getEmployees(UUID managerId) {
        Manager manager = this.managerRepository.findById(managerId).orElseThrow();
        List<EmployeeWithSupervisorsDTO> employees = new ArrayList<>();
        for (Employee e : manager.getEmployees()) {
            employees.add(this.employeeService.toDTOWithSupervisors(e));
        }
        return employees;
    }

    public ManagerDTO addSubordinate(UUID managerId , UUID employeeId){
        Manager manager = managerRepository.findById(managerId).orElse(null);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        manager.getEmployees().add(employee);
        employee.getManagers().add(manager);

        employeeRepository.save(employee);
        return toDTO( managerRepository.save(manager));



    }


    public ManagerDTO toDTO(Manager manager) {
        List<Employee> employees=manager.getEmployees();
        List<EmployeeDTO> employeesDTO=new ArrayList<>();



        ManagerDTO dto = ManagerDTO
                .builder()
                .username(manager.getUsername())
                .email(manager.getEmail())
                .hierarchy(manager.getHierarchy())
                .profilePictureURL(manager.getProfilePictureURL())
                .id(manager.getId())
                .jobName(manager.getJob().getName())
                .build();



        if (employees.size()!=0) {
            for (Employee e : employees) {
                employeesDTO.add(this.employeeService.toDTO(e));

            }
            dto.setEmployees(employeesDTO);
        }

        return dto;
    }

    public ResponseEntity<Void> deleteManagerById(UUID id) {
        Manager manager = this.managerRepository.findById(id).orElseThrow();
        managerRepository.delete(manager);
        return ResponseEntity.noContent().build();
    }


}
