package com.daeta.board.dto;

import com.daeta.board.entity.Book;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookInfoDto {
    private Long id;
    private String title;
    private String author;
    private String image;
    private String discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
    private Integer likeCount;
    private List<CommentResponseDto> commentList;
    @Builder
    private BookInfoDto(Book entity, List<CommentResponseDto> list){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.image = entity.getImage();
        this.discount = entity.getDiscount();
        this.publisher = entity.getPublisher();
        this.pubdate = entity.getPubdate();
        this.isbn = entity.getIsbn();
        this.likeCount = entity.getLikesList() !=null ? entity.getLikesList().size() : 0;
        this.commentList = list;
    }
    public BookInfoDto(Book entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.image = entity.getImage();
        this.discount = entity.getDiscount();
        this.publisher = entity.getPublisher();
        this.pubdate = entity.getPubdate();
        this.isbn = entity.getIsbn();
        this.description = entity.getDescription();
        //getLikesList() 좋아요 목록 가져온 뒤 만약 null이 아니면 size() 가져와서 likeCount 설정
        this.likeCount = entity.getLikesList() != null ?  entity.getLikesList().size() : 0;
        //getCommentList() 댓글 목록을 가져온 뒤 map(CommentResponseDto::from) 사용해서 각각의 댓글을 commentResponseDto로 반환
        this.commentList = entity.getCommentList().stream().map(CommentResponseDto::from).toList();

    }
    public static BookInfoDto from(Book entity, List<CommentResponseDto> list){
        return BookInfoDto.builder()
                .entity(entity)
                .list(list)
                .build();
    }
    public static BookInfoDto from(Book entity){
        return new BookInfoDto(entity);
    }
}
