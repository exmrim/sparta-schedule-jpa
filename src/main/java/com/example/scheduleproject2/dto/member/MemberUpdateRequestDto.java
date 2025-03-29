package com.example.scheduleproject2.dto;

import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private final String name;
    private final String email;
    private final String password;

    public MemberUpdateRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
