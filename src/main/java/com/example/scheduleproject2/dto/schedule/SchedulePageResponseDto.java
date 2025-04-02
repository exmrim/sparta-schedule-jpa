package com.example.scheduleproject2.dto.schedule;

import com.example.scheduleproject2.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePageResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String memberName;
    private final Long count;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SchedulePageResponseDto(Long id, String title, String contents, String memberName, Long count, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.count = count;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
