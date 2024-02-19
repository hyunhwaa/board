package com.daeta.board.dto;

import com.daeta.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Integer likeCount;
    private List<CommentResponseDto> commentList;

    @Builder
    private BoardResponseDto(Board entity, List<CommentResponseDto> list){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.username = entity.getUser().getUsername();
        this.createAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.likeCount = entity.getLikesList() !=null ? entity.getLikesList().size() : 0;
        this.commentList = list;
    }

    private BoardResponseDto(Board entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.username = entity.getUser().getUsername();
        this.createAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        //getLikesList() 좋아요 목록 가져온 뒤 만약 null이 아니면 size() 가져와서 likeCount 설정
        this.likeCount = entity.getLikesList() != null ?  entity.getLikesList().size() : 0;
        //getCommentList() 댓글 목록을 가져온 뒤 map(CommentResponseDto::from) 사용해서 각각의 댓글을 commentResponseDto로 반환
        this.commentList = entity.getCommentList().stream().map(CommentResponseDto::from).toList();

    }

    public static BoardResponseDto from(Board entity, List<CommentResponseDto> list){
        return BoardResponseDto.builder()
                .entity(entity)
                .list(list)
                .build();
    }

    public static BoardResponseDto from(Board entity){
        return new BoardResponseDto(entity);
    }


}
