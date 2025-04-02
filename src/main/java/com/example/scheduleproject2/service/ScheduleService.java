package com.example.scheduleproject2.service;

import com.example.scheduleproject2.dto.schedule.SchedulePageResponseDto;
import com.example.scheduleproject2.dto.schedule.ScheduleResponseDto;
import com.example.scheduleproject2.entity.Member;
import com.example.scheduleproject2.entity.Schedule;
import com.example.scheduleproject2.repository.MemberRepository;
import com.example.scheduleproject2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    //일정 생성
    public ScheduleResponseDto saveSchedule(Long id, String title, String contents) {

        Member member = memberRepository.findByIdOrElseThrow(id);

        Schedule schedule = new Schedule(title, contents);
        schedule.setMember(member);

        //repository 실행
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents()
        );
    }

    //모든 일정 조회
    public List<SchedulePageResponseDto> findScheduleAll(Long memberId, int page, int size, String criteria) {

        //Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        
        //Page객체로 데이터 생성
        Page<Schedule> scheduleList = scheduleRepository.findByMemberId(memberId, pageable);

        //모든 일정 갯수
        long totalCount = scheduleList.getTotalElements();

        //Dto 타입으로 데이터 변환 후 List에 담기
        return scheduleList
                .stream()
                .map(schedule -> new SchedulePageResponseDto(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getMember().getName(),
                        totalCount,
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()
                        ))
                .collect(Collectors.toList());
    }

    //일정 id별로 일정 조회
    public ScheduleResponseDto findScheduleById(Long id) {

        Optional<Schedule> schedule = scheduleRepository.findById(id);

        if(schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule findSchedule = schedule.get();

        return new ScheduleResponseDto(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContents()
        );
    }

    //일정 제목별로 일정 조회
    public ScheduleResponseDto findScheduleByTitle(String title) {

        Optional<Schedule> schedule = scheduleRepository.findByTitle(title);

        if(schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist title = " + title);
        }

        Schedule findSchedule = schedule.get();

        return new ScheduleResponseDto(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContents()
        );
    }

    //일정 수정
    @Transactional
    public void updateSchedule(Long id, String title, String contents) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        findSchedule.update(title, contents);
    }

    //일정 삭제
    @Transactional
    public void deleteSchedule(Long id) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        scheduleRepository.delete(findSchedule);
    }



}
