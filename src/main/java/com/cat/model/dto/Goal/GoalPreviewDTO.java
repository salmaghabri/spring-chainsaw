package com.cat.model.dto.Goal;

import com.cat.model.dto.category.CategoryDTO;
import com.cat.model.dto.validation.ValidationDTO;
import com.cat.enums.Status;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalPreviewDTO {
    private UUID id;
    private String title;
    private String createdBy;
    private String updatedBy;
    private Status status;
    private CategoryDTO category;
    private List<ValidationDTO> validations;
    private int comments;
}
