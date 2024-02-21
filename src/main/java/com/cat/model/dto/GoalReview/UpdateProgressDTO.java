package com.cat.model.dto.GoalReview;


import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProgressDTO {
    private UUID sessionId;
    private int progress;
}
