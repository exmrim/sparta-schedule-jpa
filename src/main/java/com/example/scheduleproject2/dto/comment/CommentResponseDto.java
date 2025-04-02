package com.example.scheduleproject2.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String comments;

    public CommentResponseDto(Long id, String comments) {
        this.id = id;
        this.comments = comments;
    }
}
