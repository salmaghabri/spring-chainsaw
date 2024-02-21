package com.cat.model.dto.Goal;

import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.GoalReview.GoalReviewDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalAndReviewDTO {
    private UUID id;
    private String title;
    private String description;
    private EmployeeDTO owner;
    private EmployeeDTO createdBy;
    private List<GoalReviewDTO> goalReviews;
    //todo: adjust
    private UUID reviewSession;
}
