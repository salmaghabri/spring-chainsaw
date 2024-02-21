package com.cat.model.dto.Comment;

import com.cat.model.dto.Employee.EmployeeDTO;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseComment {

    UUID id;
    String content;
    EmployeeDTO employeeDTO;

}
