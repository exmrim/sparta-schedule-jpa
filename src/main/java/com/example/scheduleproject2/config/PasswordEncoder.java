package com.example.scheduleproject2.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    //입력받은 비밀번호를 암호화 처리
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    //입력받은 비밀번호와 암호화 처리 한 비밀번호가 일치한지 확인
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }

}
