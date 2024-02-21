package com.cat.model.dto.Manager;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateManagerDTO {
    private String username;
    private String email;
    private int hierarchy;
    private String profilePictureURL;
    private String jobName;

}
