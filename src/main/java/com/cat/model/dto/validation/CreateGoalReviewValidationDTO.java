package com.cat.model.dto.validation;

import com.cat.enums.Status;
import lombok.*;

import java.util.UUID;
//todo: no usage to delete
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalReviewValidationDTO {
    private UUID validator;
    private Status status;
    private UUID sessionReview;
    private UUID goal;

}
