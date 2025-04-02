package com.example.scheduleproject2.repository;

import com.example.scheduleproject2.entity.Member;
import com.example.scheduleproject2.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findByMemberId(Long memberId,Pageable pageable);

    Optional<Schedule> findByTitle(String title);

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    default Schedule findByTitleOrElseThrow(String title) {
        return findByTitle(title).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist title = " + title));
    }

}
