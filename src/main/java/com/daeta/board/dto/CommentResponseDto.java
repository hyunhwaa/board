package com.daeta.board.dto;

import com.daeta.board.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer likeCount;
    private List<CommentResponseDto> childCommentList;

    @Builder
    private CommentResponseDto(Long id, String contents, String username, LocalDateTime createdAt, LocalDateTime modifiedAt, Integer likeCount, List<CommentResponseDto> childCommentList) {
        this.id = id;
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
        this.childCommentList = childCommentList;
    }

    public static CommentResponseDto from(Comment entity){
        return CommentResponseDto.builder()
                .id(entity.getId())
                .contents(entity.getContents())
                .username(entity.getUser().getUsername())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .likeCount((int) entity.getLikeList().stream().count())
                .childCommentList(entity.getChildCommentList().stream().map(CommentResponseDto::from).toList())
                .build();
    }
}
