package com.daeta.board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Likes {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id",  referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    private Likes(Book book, Board board, Comment comment, User user){
        this.book = book;
        this.board = board;
        this.comment = comment;
        this.user = user;
    }

    public static Likes of(Book book, User user){
        Likes likes = Likes.builder()
                .book(book)
                .user(user)
                .build();
        book.getLikesList().add(likes);

        return likes;
    }

    public static Likes of(Comment comment, User user){
        Likes likes = Likes.builder()
                .comment(comment)
                .user(user)
                .book(comment.getBook())
                .build();
        comment.getLikeList().add(likes);

        return likes;
    }
    public static Likes of(Board board, User user){
        Likes likes = Likes.builder()
                .board(board)
                .user(user)
                .build();
        board.getLikesList().add(likes);

        return likes;
    }
}
