package com.cat.model.dto.SessionReview;

import com.cat.model.dto.Goal.GoalAndReviewDTO;
import com.cat.model.dto.validation.ValidationDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionReviewDTO {
    private UUID id;
    private String type;
    private LocalDate date;
    private int ranking;
    private List<ValidationDTO> validations;
    private List<GoalAndReviewDTO> goals;

}
