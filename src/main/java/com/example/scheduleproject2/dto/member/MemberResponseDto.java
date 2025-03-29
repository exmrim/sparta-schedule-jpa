package com.example.scheduleproject2.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final String name;
    private final String email;

    public MemberResponseDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
