package com.example.scheduleproject2.dto.schedule;

import com.example.scheduleproject2.entity.Member;
import lombok.Getter;

@Getter
public class ScheduleCreateDto {

    private final Long memberId;
    private final String title;
    private final String contents;


    public ScheduleCreateDto(Long memberId, String title, String contents) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
    }
}
