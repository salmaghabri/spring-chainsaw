package com.cat.model.dto.validation;

import com.cat.enums.Status;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewSessionValidationDTO {
    private UUID validator;
    private Status status;
    private UUID SessionReview;
}
