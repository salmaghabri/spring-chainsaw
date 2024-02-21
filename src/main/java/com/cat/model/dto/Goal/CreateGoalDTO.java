package com.cat.model.dto.Goal;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalDTO {
    private String title;
    private String description;
    private UUID createdBy;
    private UUID gps;
    private UUID category;
}
