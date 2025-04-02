package com.example.scheduleproject2.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final Long memberId;
    private final String comments;

    public CommentRequestDto(Long memberId, String comments) {
        this.memberId = memberId;
        this.comments = comments;
    }
}
