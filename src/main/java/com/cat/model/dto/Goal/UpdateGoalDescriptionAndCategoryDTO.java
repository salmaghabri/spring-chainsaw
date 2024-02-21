package com.cat.model.dto.Goal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGoalDescriptionAndCategoryDTO {
    private String description;
    private UUID category;
    private UUID updatedBy;
}
