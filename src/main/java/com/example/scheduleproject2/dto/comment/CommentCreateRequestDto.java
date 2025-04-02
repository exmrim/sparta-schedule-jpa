package com.example.scheduleproject2.dto.comment;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CommentCreateRequestDto {

    private final Long memberId;
    private final Long scheduleId;

    @Range(max = 50)
    private final String comments;

    public CommentCreateRequestDto(Long memberId, Long scheduleId, String comments) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.comments = comments;
    }
}
