package com.cat.model.dto.validation;

import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.enums.Status;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDTO {
    private UUID id;
    private EmployeeDTO validator;
    private Status status;
    private int ranking;
    private Status goalStatus;

}
