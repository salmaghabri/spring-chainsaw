package com.cat.model.dto.SessionReview;

import com.cat.model.dto.GoalReview.GoalReviewDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSessionWithGoalReviews {
    private UUID id;
    private String type;
    private LocalDate date;
    private int ranking;
    private List<GoalReviewDTO> goalReviews;
}
