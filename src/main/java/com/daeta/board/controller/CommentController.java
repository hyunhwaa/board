package com.daeta.board.controller;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.common.ErrorResponse;
import com.daeta.board.common.ResponseUtils;
import com.daeta.board.common.SuccessResponse;
import com.daeta.board.dto.CommentRequestDto;
import com.daeta.board.dto.CommentResponseDto;
import com.daeta.board.entity.Comment;
import com.daeta.board.entity.User;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.repository.CommentRepository;
import com.daeta.board.repository.UserRepository;
import com.daeta.board.security.UserDetailsImpl;
import com.daeta.board.service.CommentService;
import com.daeta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     */
    @PostMapping("/comment/{id}")
    public ApiResponseDto<CommentResponseDto> createComment(@PathVariable Long id,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    /**
     * 댓글 수정
     */
    @GetMapping("/comment/{id}")
    public ApiResponseDto<CommentResponseDto> getComment(@PathVariable Long id){
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isEmpty()) {
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        Comment findComment = comment.get();
        CommentResponseDto commentResponseDto = CommentResponseDto.from(findComment);
        System.out.println("GET" + commentResponseDto.getContents());
        // 댓글 내용을 ApiResponseDto로 감싸서 반환.
        return ResponseUtils.ok(commentResponseDto);
    }

    @PutMapping("/comment/{id}")
    public ApiResponseDto<CommentResponseDto> updateComment(@PathVariable Long id,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("PUT"+requestDto.getContents());
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comment/{id}")
    public ApiResponseDto<SuccessResponse> deleteComment(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }


}