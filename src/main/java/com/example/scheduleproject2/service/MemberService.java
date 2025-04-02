package com.example.scheduleproject2.service;

import com.example.scheduleproject2.config.PasswordEncoder;
import com.example.scheduleproject2.dto.comment.CommentResponseDto;
import com.example.scheduleproject2.dto.member.LoginResponseDto;
import com.example.scheduleproject2.dto.member.MemberResponseDto;
import com.example.scheduleproject2.dto.member.SignUpResponseDto;
import com.example.scheduleproject2.entity.Comment;
import com.example.scheduleproject2.entity.Member;
import com.example.scheduleproject2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public SignUpResponseDto signUp(String name, String email, String password) {

        //입력받은 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        //입력받은 비밀번호와 암호화 된 비밀번호 일치한지 확인
        if(!passwordEncoder.matches(password, encodedPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        //Member 객체에 데이터 담기
        Member member = new Member(name, email, encodedPassword);

        //생성된 Member를 다시 담기
        Member savedMember = memberRepository.save(member);

        //반환
        return new SignUpResponseDto(savedMember.getId(), savedMember.getName(), savedMember.getEmail(), savedMember.getPassword());

    }

    //로그인
    public LoginResponseDto login(String email, String password) {

        //로그인 할 때 입력받은 이메일로 사용자 조회
        Optional<Member> member = memberRepository.findByEmail(email);
        
        //해당 이메일이 존재하지 않을 경우 에러
        if(!email.equals(member.get().getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        //조회한 비밀번호와 입력받은 비밀번호 일치한지 조회
        if(!passwordEncoder.matches(password, member.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        //해당 정보로 사용자 조회
        Optional<Member> findMember = memberRepository.findIdByEmailAndPassword(email, member.get().getPassword());

        //조회한 사용자 식별 id
        Long index = findMember.get().getId();

        //반환
        return new LoginResponseDto(index);
    }

    //모든 사용자 조회
    public List<MemberResponseDto> findAll(int page, int size, String criteria) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Member> memberList = memberRepository.findAll(pageable);

        return memberList
                .stream()
                .map(member -> new MemberResponseDto(member.getId(), member.getName(), member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
    }

    //사용자 id별 사용자 조회
    public MemberResponseDto findMemberById(Long id) {

        //Optional
        Optional<Member> member = memberRepository.findById(id);

        //NPE 방지
        if(member.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Member findMember = member.get();

        return new MemberResponseDto(findMember.getId(), findMember.getName(), findMember.getEmail(), findMember.getPassword());
    }

    //사용자 email별로 사용자 조회
    public MemberResponseDto findMemberByEmail(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist email = " + email);
        }

        Member findMember = member.get();

        return new MemberResponseDto(findMember.getId(), findMember.getName(), findMember.getEmail(), findMember.getPassword());
    }

    //사용자 이름별로 사용자 조회
    public MemberResponseDto findMemberByName(String name) {

        Optional<Member> member = memberRepository.findByName(name);

        if(member.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist name = " + name);
        }

        Member findMember = member.get();

        return new MemberResponseDto(findMember.getId(), findMember.getName(), findMember.getEmail(), findMember.getPassword());
    }

    //사용자 수정
    @Transactional
    public void updateMember(Long id, String name, String email) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        findMember.update(name, email);
    }

    //사용자 비밀번호 수정
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        //비밀번호 일치 확인
        if(!findMember.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid old password");
        }

        findMember.updatePassword(newPassword);
    }

    //사용자 삭제
    @Transactional
    public void deleteMember(Long id, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        //비밀번호 인증 처리

        memberRepository.delete(findMember);

    }


}
