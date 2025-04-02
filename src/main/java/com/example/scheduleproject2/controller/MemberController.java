package com.example.scheduleproject2.controller;

import com.example.scheduleproject2.dto.comment.CommentResponseDto;
import com.example.scheduleproject2.dto.member.*;
import com.example.scheduleproject2.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpResponseDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = memberService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findMemberAll(
            @RequestParam(required = false, defaultValue = "0", value = "page") int page,
            @RequestParam(required = false, defaultValue = "10", value = "size") int size,
            @RequestParam(required = false, defaultValue = "updatedAt", value = "criteria") String criteria
    ) {
        List<MemberResponseDto> memberResponseDtoList = memberService.findAll(page, size, criteria);
        return new ResponseEntity<>(memberResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(@PathVariable Long id) {
        MemberResponseDto memberResponseDto = memberService.findMemberById(id);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<MemberResponseDto> findMemberByName(@PathVariable String name) {
        MemberResponseDto memberResponseDto = memberService.findMemberByName(name);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMember(
            @PathVariable Long id,
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.updateMember(id, memberUpdateRequestDto.getName(), memberUpdateRequestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id,
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.deleteMember(id, memberUpdateRequestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
