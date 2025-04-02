package com.example.scheduleproject2.repository;


import com.example.scheduleproject2.entity.Comment;
import com.example.scheduleproject2.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findAll(Pageable pageable);

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    Optional<Member> findIdByEmailAndPassword(String email, String password);

    default Member findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    default Member findByNameOrElseThrow(String name) {
        return findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist name = " + name));
    }


}
