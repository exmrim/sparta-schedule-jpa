package com.example.scheduleproject2.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class ScheduleUpdateRequestDto {

    @NotBlank
    @Range(max = 10)
    private final String title;

    @NotBlank
    @Range(max = 50)
    private final String contents;

    public ScheduleUpdateRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
