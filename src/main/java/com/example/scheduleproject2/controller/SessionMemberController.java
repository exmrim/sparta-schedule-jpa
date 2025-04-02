package com.example.scheduleproject2.controller;

import com.example.scheduleproject2.common.Const;
import com.example.scheduleproject2.dto.member.LoginRequestDto;
import com.example.scheduleproject2.dto.member.LoginResponseDto;
import com.example.scheduleproject2.dto.member.MemberResponseDto;
import com.example.scheduleproject2.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class SessionMemberController {

    private final MemberService memberService;

    @PostMapping("/session-login")
    public String login(
            @Valid @ModelAttribute LoginRequestDto dto,
            HttpServletRequest request
    ) {

        LoginResponseDto responseDto = memberService.login(dto.getEmail(), dto.getPassword());
        Long memberId = responseDto.getId();

        // 실패시 예외처리
        if (memberId == null) {
            return "session-login";
        }

        HttpSession session = request.getSession();

        // 회원 정보 조회
        MemberResponseDto loginMember = memberService.findMemberById(memberId);

        //이메일, 비밀번호 안 맞을 시 401 예외처리
        if(!loginMember.getEmail().equals(dto.getEmail()) || !loginMember.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

        session.setAttribute(Const.LOGIN_MEMBER, loginMember);

        return "redirect:/session-home";
    }

    @PostMapping("/session-logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        // 세션이 존재하면 로그인이 된 경우
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/session-home";
    }
}
