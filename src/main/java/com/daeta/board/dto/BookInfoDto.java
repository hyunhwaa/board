package com.daeta.board.dto;

import com.daeta.board.entity.Book;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
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
    private BookInfoDto(Long id, String title, String author, String image, String discount,
                        String publisher, String pubdate, String isbn, String description,
                        Integer likeCount, List<CommentResponseDto> commentList) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.discount = discount;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.isbn = isbn;
        this.description = description;
        this.likeCount = likeCount;
        this.commentList = commentList;
    }

    public static BookInfoDto from(Book entity, List<CommentResponseDto> list) {
        return BookInfoDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .image(entity.getImage())
                .discount(entity.getDiscount())
                .publisher(entity.getPublisher())
                .pubdate(entity.getPubdate())
                .isbn(entity.getIsbn())
                .description(entity.getDescription())
                .likeCount(entity.getLikesList() != null ? entity.getLikesList().size() : 0)
                .commentList(list)
                .build();
    }

    public static BookInfoDto from(Book entity) {
        return BookInfoDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .image(entity.getImage())
                .discount(entity.getDiscount())
                .publisher(entity.getPublisher())
                .pubdate(entity.getPubdate())
                .isbn(entity.getIsbn())
                .description(entity.getDescription())
                .likeCount(entity.getLikesList() != null ? entity.getLikesList().size() : 0)
                .commentList(entity.getCommentList().stream().map(CommentResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}