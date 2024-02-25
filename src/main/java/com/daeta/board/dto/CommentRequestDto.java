package com.daeta.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentRequestDto {
    String contents;
    Long parentCommentId;
    String isbn;
}
