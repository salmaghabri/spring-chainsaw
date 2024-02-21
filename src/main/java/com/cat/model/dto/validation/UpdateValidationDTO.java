package com.cat.model.dto.validation;

import com.cat.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateValidationDTO {
    private Status status;
}
