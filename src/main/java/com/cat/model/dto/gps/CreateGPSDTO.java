package com.cat.model.dto.gps;

import com.cat.model.dto.SessionReview.CreateSessionReviewDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGPSDTO {
    private int year;
    private int  sessionNumber;
    private int minGoalsNumber;
    private UUID employee;
    private List<CreateSessionReviewDTO> sessionReviews;
}
