package com.example.scheduleproject2.controller;

import com.example.scheduleproject2.dto.comment.CommentCreateRequestDto;
import com.example.scheduleproject2.dto.comment.CommentResponseDto;
import com.example.scheduleproject2.dto.comment.CommentUpdateRequestDto;
import com.example.scheduleproject2.dto.schedule.ScheduleResponseDto;
import com.example.scheduleproject2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentCreateRequestDto commentCreateDto) {
        CommentResponseDto commentResponseDto = commentService.saveComment(commentCreateDto.getMemberId(), commentCreateDto.getScheduleId(), commentCreateDto.getComments());
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findCommentAll(
            @RequestParam(required = false, defaultValue = "0", value = "page") int page,
            @RequestParam(required = false, defaultValue = "10", value = "size") int size,
            @RequestParam(required = false, defaultValue = "updatedAt", value = "criteria") String criteria
    ) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAll(page, size, criteria);
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CommentResponseDto> findCommentById(@PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.findCommentById(id);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CommentResponseDto> findCommentByMemberEmail(@PathVariable String email) {
        CommentResponseDto commentResponseDto = commentService.findCommentByMemberEmail(email);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long id,
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ) {
        commentService.updateComment(id, commentUpdateRequestDto.getComments());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
