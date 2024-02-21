package com.cat.model.dto.Goal;

import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.category.CategoryDTO;
import com.cat.model.dto.validation.ValidationDTO;
import com.cat.enums.Status;
import lombok.*;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalDTO {
    private UUID id;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private EmployeeDTO createdBy;
    private EmployeeDTO updatedBy;
    private Status status;
    private CategoryDTO category;
    private EmployeeDTO owner;
//    private List<ResponseComment> comments;
    private List<ValidationDTO> validations;
    private UUID gps;
    //todo: uncomment
//    private List<GoalReviewDTO> goalReviews;


}
