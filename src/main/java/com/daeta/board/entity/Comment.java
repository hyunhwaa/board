package com.daeta.board.entity;

import com.daeta.board.dto.CommentRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends Timestamped{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 2000)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Likes> likeList = new ArrayList<>();

    @Column
    private Long parentCommentId;

    @OrderBy("createdAt asc")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private List<Comment> childCommentList = new ArrayList<>();

    @Builder
    private Comment(CommentRequestDto requestDto, Board board, Book book, User user){
        this.contents = requestDto.getContents();
        this.parentCommentId = requestDto.getParentCommentId();
        this.book = book;
        this.board = board;
        this.user = user;
    }
    public void update(CommentRequestDto requestDto, User user){
        this.contents = requestDto.getContents();
        this.user = user;
    }
    public static Comment of(CommentRequestDto requestDto,Book book, User user){
        Comment comment = Comment.builder()
                .requestDto(requestDto)
                .book(book)
                .user(user)
                .build();

        book.getCommentList().add(comment);
        return comment;
    }
    public void addChildComment(Comment child){
        this.getChildCommentList().add(child);
    }

    public static Comment of(CommentRequestDto requestDto, Board board, User user){
        Comment comment = Comment.builder()
                .requestDto(requestDto)
                .board(board)
                .user(user)
                .build();

        board.getCommentList().add(comment);
        return comment;
    }
}
