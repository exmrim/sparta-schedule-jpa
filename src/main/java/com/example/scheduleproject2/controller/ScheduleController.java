package com.example.scheduleproject2.controller;

import com.example.scheduleproject2.dto.schedule.ScheduleCreateRequestDto;
import com.example.scheduleproject2.dto.schedule.SchedulePageResponseDto;
import com.example.scheduleproject2.dto.schedule.ScheduleResponseDto;
import com.example.scheduleproject2.dto.schedule.ScheduleUpdateRequestDto;
import com.example.scheduleproject2.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleCreateRequestDto scheduleCreateDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.saveSchedule(scheduleCreateDto.getMemberId(), scheduleCreateDto.getTitle(), scheduleCreateDto.getContents());
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/memberId/{memberId}")
    public ResponseEntity<List<SchedulePageResponseDto>> findScheduleAll(
            @PathVariable Long memberId,
            @RequestParam(required = false, defaultValue = "0", value = "page") int page,
            @RequestParam(required = false, defaultValue = "10", value = "size") int size,
            @RequestParam(required = false, defaultValue = "updatedAt", value = "criteria") String criteria
    ) {
        List<SchedulePageResponseDto> schedulePageResponseDtoList = scheduleService.findScheduleAll(memberId, page, size, criteria);
        return new ResponseEntity<>(schedulePageResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/scheduleId/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.findScheduleById(id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<ScheduleResponseDto> findScheduleByTitle(@PathVariable String title) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.findScheduleByTitle(title);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto
            ) {
        scheduleService.updateSchedule(id, scheduleUpdateRequestDto.getTitle(), scheduleUpdateRequestDto.getContents());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
