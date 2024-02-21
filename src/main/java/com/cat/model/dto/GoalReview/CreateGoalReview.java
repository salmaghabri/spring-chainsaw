package com.cat.model.dto.GoalReview;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalReview {
    private UUID createdBy;
    private UUID goal;
    private UUID reviewSession;
    private int progress;
}
