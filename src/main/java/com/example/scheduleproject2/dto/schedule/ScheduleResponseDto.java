package com.example.scheduleproject2.dto.schedule;

import com.example.scheduleproject2.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;


    public ScheduleResponseDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

}
