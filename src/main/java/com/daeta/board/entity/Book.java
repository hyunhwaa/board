package com.daeta.board.entity;

import com.daeta.board.dto.BookInfoDto;
import com.daeta.board.dto.CommentResponseDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column
    private String author;

    @Column
    private String image;

    @Column
    private String discount;

    @Column
    private String publisher;

    @Column
    private String pubdate;

    @Column(length = 100000)
    private String description;


    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    public Book(BookInfoDto bookInfoDto){
        this.id = bookInfoDto.getId();
        this.isbn = bookInfoDto.getIsbn();
        this.title = bookInfoDto.getTitle();
        this.author = bookInfoDto.getAuthor();
        this.image = bookInfoDto.getImage();
        this.discount = bookInfoDto.getDiscount();
        this.publisher = bookInfoDto.getPublisher();
        this.pubdate = bookInfoDto.getPubdate();
        this.isbn = bookInfoDto.getIsbn();
        this.description = bookInfoDto.getDescription();
    }
    public static Book of(BookInfoDto bookInfoDto){
        return new Book((bookInfoDto));
    }
}
