package com.cat.model.dto.SessionReview;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionReviewPreviewDTO {
    private UUID id;
    private int ranking;
    private LocalDate date;
    private String type;
}
