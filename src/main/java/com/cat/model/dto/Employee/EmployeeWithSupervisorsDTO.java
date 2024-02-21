package com.cat.model.dto.Employee;

import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeWithSupervisorsDTO {
    private UUID id;
    private String username;
    private String email;
    private int hierarchy;
    private String profilePictureURL;
    private String jobName;
    private List<EmployeeDTO> supervisors;
//    private List<EmployeeDTO> managers;
}
