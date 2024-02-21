package com.cat.model.dto.gps;

import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.Goal.GoalPreviewDTO;
import com.cat.model.dto.SessionReview.SessionReviewPreviewDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPSDTO {
    private UUID id;
    private int year;
    private int sessionNumber;
    private int minGoalsNumber;
    private EmployeeDTO employee;
    private List<GoalPreviewDTO> goals;
    private List<SessionReviewPreviewDTO> sessionReviews;

}
