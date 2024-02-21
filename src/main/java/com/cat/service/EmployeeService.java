package com.cat.service;

import com.cat.model.dao.Employee;
import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.repository.EmployeeRepository;
import com.cat.model.dao.Job;
import com.cat.model.dao.Manager;
import com.cat.model.dao.Supervisor;
import com.cat.model.dto.Employee.CreateEmployeeDTO;
import com.cat.model.dto.Employee.EmployeeWithSupervisorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PropertyDescriptor;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final JobService jobService;
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(UUID id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + id));
        return employee;
    }
    public EmployeeDTO getEmployeeDTOById(UUID id){
        return toDTO(getEmployeeById(id));
    }


    public EmployeeDTO createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        Employee employee = new Employee();
        employee.setUsername(createEmployeeDTO.getUsername());
        employee.setEmail(createEmployeeDTO.getEmail());
        employee.setHierarchy(createEmployeeDTO.getHierarchy());
        employee.setProfilePictureURL(createEmployeeDTO.getProfilePictureURL());

        Job job = jobService.getJobByName(createEmployeeDTO.getJobName());
        employee.setJob(job);
        return this.toDTO(this.employeeRepository.save(employee));
    }

    public Employee updateEmployee(UUID id, Employee employeeDetails) {
        Employee employee = this.getEmployeeById(id);
        BeanUtils.copyProperties(employeeDetails, employee, getNullPropertyNames(employeeDetails));

        return this.employeeRepository.save(employee);
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

    public ResponseEntity<Employee> deleteEmployeeById(UUID id) {
        Employee employee = this.getEmployeeById(id);
        this.employeeRepository.delete(employee);
        return ResponseEntity.noContent().build();
    }

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = EmployeeDTO.builder()
                .username(employee.getUsername())
                .email(employee.getEmail()).
                hierarchy(employee.getHierarchy())
                .profilePictureURL((employee.getProfilePictureURL()))
                .id(employee.getId())
                .jobName(employee.getJob().getName())
                .build();
        return dto;


    }
    public EmployeeWithSupervisorsDTO toDTOWithSupervisors(Employee employee){
        EmployeeWithSupervisorsDTO dto=EmployeeWithSupervisorsDTO
                .builder()
                .username(employee.getUsername())
                .email(employee.getEmail()).
                hierarchy(employee.getHierarchy())
                .profilePictureURL((employee.getProfilePictureURL()))
                .id(employee.getId())
                .jobName(employee.getJob().getName())
                .supervisors(getAllSupervisors(employee.getId()))
//                .managers(getManagers(employee.getId()))
                .build();
        return dto;


    }
    public List<EmployeeDTO> getManagers(UUID employeeId){
        Employee employee=this.employeeRepository.findById(employeeId).orElseThrow();
        List<Manager> managersList=employee.getManagers();
        if(managersList.size()==0 )
            return new ArrayList<>();
        List<EmployeeDTO> managers=new ArrayList<>();
        for(Manager m: managersList){
            managers.add(toDTO(m));


        }
        return managers;





    }

    public Employee toEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setUsername(employeeDTO.getUsername());
        employee.setEmail(employeeDTO.getEmail());
        employee.setHierarchy(employeeDTO.getHierarchy());
        employee.setProfilePictureURL(employeeDTO.getProfilePictureURL());

        Job job = jobService.getJobByName(employeeDTO.getJobName());
        employee.setJob(job);
        return employee;


    }

    public List<EmployeeDTO> getAllSupervisors(UUID employeeId){
        Employee employee=this.employeeRepository.findById(employeeId).orElseThrow();
        List<Supervisor> supervisorsList=employee.getSupervisors();
        if(supervisorsList.size()==0 )
            return new ArrayList<>();
        List<EmployeeDTO> supervisors=new ArrayList<>();
        Supervisor supervisor= supervisorsList.get(0);
        supervisors.add(toDTO(supervisor));
        while(supervisor.getSupervisors()!= null &&  supervisor.getSupervisors().size() >0 ){ // stop if the supervisor no longer has supervisors
        supervisor= supervisor.getSupervisors().get(0);
            supervisors.add(toDTO(supervisor));

        }
        return supervisors;


    }

}

