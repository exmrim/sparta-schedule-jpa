package com.example.scheduleproject2.service;

import com.example.scheduleproject2.dto.comment.CommentResponseDto;
import com.example.scheduleproject2.dto.schedule.ScheduleResponseDto;
import com.example.scheduleproject2.entity.Comment;
import com.example.scheduleproject2.entity.Member;
import com.example.scheduleproject2.entity.Schedule;
import com.example.scheduleproject2.repository.CommentRepository;
import com.example.scheduleproject2.repository.MemberRepository;
import com.example.scheduleproject2.repository.ScheduleRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    //댓글 생성 처리
    public CommentResponseDto saveComment(Long memberId, Long scheduleId, String comments) {

        Member member = memberRepository.findByIdOrElseThrow(memberId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(comments);
        comment.setMember(member);
        comment.setSchedule(schedule);

        //repository 실행
        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getId(),
                comment.getComments()
        );
    }

    //모든 댓글 조회 처리
    public List<CommentResponseDto> findAll(int page, int size, String criteria) {

        //Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        
        //Page 객체로 데이터 담기
        Page<Comment> commentList = commentRepository.findAll(pageable);

        //Dto 형태로 데이터 변환해서 List에 담기
        return commentList
                .stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getComments()))
                .collect(Collectors.toList());
    }

    //댓글 id별 댓글 조회
    public CommentResponseDto findCommentById(Long id) {

        Optional<Comment> comment = commentRepository.findById(id);

        if(comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Comment findComment = comment.get();

        return new CommentResponseDto(
                findComment.getId(),
                findComment.getComments()
        );
    }

    //사용자 email별로 댓글 조회
    public CommentResponseDto findCommentByMemberEmail(String email) {

        Optional<Comment> comment = commentRepository.findByMemberEmail(email);

        if(comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist email = " + email);
        }

        Comment findComment = comment.get();

        return new CommentResponseDto(
                findComment.getId(),
                findComment.getComments()
        );
    }

    //댓글 수정
    @Transactional
    public void updateComment(Long id, String comments) {

        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        findComment.update(comments);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long id) {

        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        commentRepository.delete(findComment);
    }
}
