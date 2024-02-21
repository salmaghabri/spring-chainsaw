package com.cat.model.dto.SessionReview;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    CreateSessionReviewDTO {
    private String type;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private UUID gps;
    private UUID gpsOwner;

}
