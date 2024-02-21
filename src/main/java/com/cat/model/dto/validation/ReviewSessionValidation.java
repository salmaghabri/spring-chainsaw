package com.cat.model.dto.validation;

import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSessionValidation {
    private UUID id;
    private EmployeeDTO validator;
    private Status status;
    private int ranking;
}
