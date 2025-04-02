package com.example.scheduleproject2.controller;


import com.example.scheduleproject2.common.Const;
import com.example.scheduleproject2.dto.member.MemberResponseDto;
import com.example.scheduleproject2.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class SessionHomeController {

    private final MemberService memberService;

    @GetMapping("/session-home")
    public String home(
            HttpServletRequest request,
            Model model
    ) {

        HttpSession session = request.getSession(false);

        // session이 없으면 로그인 페이지로 이동
        if(session == null) {
            return "session-login";
        }

        MemberResponseDto loginMember = (MemberResponseDto) session.getAttribute(Const.LOGIN_MEMBER);

        if (loginMember == null) {
            return "session-login";
        }

        model.addAttribute("loginMember", loginMember);

        return "session-home";

    }

    @GetMapping("/v2/session-home")
    public String homeV2(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember,
            Model model
    ) {

        if (loginMember == null) {
            return "session-login";
        }

        model.addAttribute("loginMember", loginMember);

        return "session-home";
    }

}
