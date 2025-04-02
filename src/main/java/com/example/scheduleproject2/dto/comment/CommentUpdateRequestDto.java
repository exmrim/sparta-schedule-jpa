package com.example.scheduleproject2.dto.comment;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CommentUpdateRequestDto {

    @Range(min=1, max = 50)
    private final String comments;

    public CommentUpdateRequestDto(String comments) {
        this.comments = comments;
    }

}
