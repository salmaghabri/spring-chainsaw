package com.cat.service;

import com.cat.model.dao.Employee;
import com.cat.model.dao.Job;
import com.cat.model.dao.Manager;
import com.cat.model.dao.Supervisor;
import com.cat.model.dto.Employee.CreateEmployeeDTO;
import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.Employee.EmployeeWithSupervisorsDTO;
import com.cat.model.dto.supervisor.SupervisorDTO;
import com.cat.repository.EmployeeRepository;
import com.cat.repository.SupervisorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final JobService jobService;

    public Page<SupervisorDTO> getAllSupervisors(Pageable pageable) {
        Page<Supervisor> supervisorsPage = supervisorRepository.findAll(pageable);
        List<SupervisorDTO> supervisorDTOList = supervisorsPage.getContent()
                .stream()
                .map(this::toDTO )
                .collect(Collectors.toList());

        return new PageImpl<>(supervisorDTOList, pageable, supervisorsPage.getTotalElements());
    }

    public SupervisorDTO getSupervisorById(UUID id) {
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supervisor not found with ID: " + id));
        return toDTO(supervisor);
    }

    public Supervisor createSupervisor(CreateEmployeeDTO createSupervisorDTO) {
        Supervisor supervisor = new Supervisor();
        supervisor.setUsername(createSupervisorDTO.getUsername());
        supervisor.setEmail(createSupervisorDTO.getEmail());
        supervisor.setHierarchy(createSupervisorDTO.getHierarchy());
        supervisor.setProfilePictureURL(createSupervisorDTO.getProfilePictureURL());

        Job job = jobService.getJobByName(createSupervisorDTO.getJobName());
        supervisor.setJob(job);

        return supervisorRepository.save(supervisor);
    }

    public Supervisor updateSupervisor(UUID id, Supervisor supervisorDetails) {
        Supervisor supervisor = this.supervisorRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(supervisorDetails, supervisor, getNullPropertyNames(supervisorDetails));

        return supervisorRepository.save(supervisor);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
            Object srcValue = srcWrapper.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    public ResponseEntity<Supervisor> deleteSupervisorById(UUID id) {
        Supervisor supervisor = this.supervisorRepository.findById(id).orElseThrow();
        supervisorRepository.delete(supervisor);
        return ResponseEntity.noContent().build();
    }

    public SupervisorDTO toDTO(Supervisor supervisor) {
        List<Employee> employees=supervisor.getEmployees();
        List<EmployeeDTO> employeesDTO=new ArrayList<>();
        List<Manager> managers=supervisor.getManagers();
        List<EmployeeDTO> managersDTO=new ArrayList<>();
        List<Supervisor> supervisors=supervisor.getSupervisors();
        List<EmployeeDTO> supervisorsDTO=new ArrayList<>();


        SupervisorDTO dto = SupervisorDTO
                .builder()
                .username(supervisor.getUsername())
                .email(supervisor.getEmail())
                .hierarchy(supervisor.getHierarchy())
                .profilePictureURL(supervisor.getProfilePictureURL())
                .id(supervisor.getId())
                .jobName(supervisor.getJob().getName())
                .build();

        if (managers.size()!=0) {
            for (Employee e : managers) {
                managersDTO.add(this.employeeService.toDTO(e));

            }
            dto.setManagers(managersDTO);
        }

        if (employees.size()!=0) {
            for (Employee e : employees) {
                employeesDTO.add(this.employeeService.toDTO(e));

            }
            dto.setEmployees(employeesDTO);
        }
        if (supervisors.size()!=0) {
            for (Employee e : supervisors) {
                supervisorsDTO.add(this.employeeService.toDTO(e));
            }
            dto.setSupervisors(supervisorsDTO);
        }
        return dto;
    }

    public Supervisor toSupervisor(SupervisorDTO supervisorDTO) {
        Supervisor supervisor = new Supervisor();
        supervisor.setUsername(supervisorDTO.getUsername());
        supervisor.setEmail(supervisorDTO.getEmail());
        supervisor.setHierarchy(supervisorDTO.getHierarchy());
        supervisor.setProfilePictureURL(supervisorDTO.getProfilePictureURL());

        Job job = jobService.getJobByName(supervisorDTO.getJobName());
        supervisor.setJob(job);

        return supervisor;
    }
    public SupervisorDTO addSubordinate(UUID employeeId, UUID supervisorId){
        Supervisor supervisor = supervisorRepository.findById(supervisorId).orElse(null);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (supervisor == null || employee == null) {
            throw new EntityNotFoundException("Supervisor or Employee not found");
        }
        List<EmployeeDTO> supervisors=this.employeeService.getAllSupervisors(supervisorId);
        for(EmployeeDTO dto: supervisors ){
            Supervisor supervisorFromDTO = supervisorRepository.findById(dto.getId()).orElse(null);
            supervisorFromDTO.getEmployees().add(employee);
            supervisorRepository.save(supervisorFromDTO);



        }
        supervisor.getEmployees().add(employee);
        if(supervisor.getHierarchy()-employee.getHierarchy() ==1 ) {
            employee.getSupervisors().add(supervisor);
        }


        supervisor = supervisorRepository.save(supervisor);



        return this.toDTO(supervisor);



    }


    public List<EmployeeWithSupervisorsDTO> getEmployees(UUID supervisorId) {
        Supervisor supervisor=this.supervisorRepository.findById(supervisorId).orElseThrow();
        List<EmployeeWithSupervisorsDTO> employees = new ArrayList<>();
        for( Employee e: supervisor.getEmployees()){
            employees.add(this.employeeService.toDTOWithSupervisors(e));
        }
        return employees;

    }
}

