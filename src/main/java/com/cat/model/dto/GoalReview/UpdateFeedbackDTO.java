package com.cat.model.dto.GoalReview;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFeedbackDTO {
    private UUID sessionId;
    private String feedback;
}
