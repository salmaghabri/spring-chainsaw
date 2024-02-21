package com.cat.model.dto.Employee;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private UUID id;
    private String username;
    private String email;
    private int hierarchy;
    private String profilePictureURL;
    private String jobName;
}
