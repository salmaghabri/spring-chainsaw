package com.cat.model.dto.GoalReview;

import com.cat.model.dto.Employee.EmployeeDTO;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalReviewDTO {
    private UUID id;
    private Date createdAt;
    private int progress;
    private String feedback;
    private EmployeeDTO createdBy;
    private UUID goal;
    private UUID reviewSession;
//    private ValidationDTO validation;


}
