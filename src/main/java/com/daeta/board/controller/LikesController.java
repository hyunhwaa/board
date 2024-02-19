package com.daeta.board.controller;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.dto.BookInfoDto;
import com.daeta.board.dto.CommentResponseDto;
import com.daeta.board.entity.Comment;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.repository.CommentRepository;
import com.daeta.board.security.UserDetailsImpl;
import com.daeta.board.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likesService;
    private final CommentRepository commentRepository;

    /**
     * 게시글 좋아요
     */
    @PutMapping("/post/{id}")
    public ApiResponseDto<?> likePost(@PathVariable Long id,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likesService.likePost(id, userDetails.getUser());
    }

    /**
     * 댓글 좋아요
     */
    @PutMapping("/comment/{id}")
    public ApiResponseDto<CommentResponseDto> likeComment(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likesService.likeComment(id, userDetails.getUser());
    }
}
