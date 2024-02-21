package com.cat.model.dto.Employee;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDTO {
    private String username;
    private String email;
    private int hierarchy;
    private String profilePictureURL;
    private String jobName;
}
