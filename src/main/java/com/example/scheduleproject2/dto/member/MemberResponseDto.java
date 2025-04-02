package com.example.scheduleproject2.dto.member;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    public MemberResponseDto(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
