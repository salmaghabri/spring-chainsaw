package com.cat.model.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {
    private String content;
    private UUID commentedBy;
    private UUID goal;
}
