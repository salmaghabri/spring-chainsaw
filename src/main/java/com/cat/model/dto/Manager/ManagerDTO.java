package com.cat.model.dto.Manager;

import com.cat.model.dto.Employee.EmployeeDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private UUID id;
    private String username;
    private String email;
    private int hierarchy;
    private String profilePictureURL;
    private String jobName;
    private List<EmployeeDTO> employees;
}
