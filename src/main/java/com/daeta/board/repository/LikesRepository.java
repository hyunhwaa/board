package com.daeta.board.repository;

import com.daeta.board.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    //특정 게시글 좋아요
    Optional<Likes> findByBookAndUser(Book book, User user);
    Optional<Likes> findByBoardAndUser(Board board, User user);

    //특정 댓글 좋아요
    Optional<Likes> findByCommentAndUser(Comment comment, User user);

    //특정 사용자가 좋아요한 게시글 목록
    List<Likes> findByUser(User user);

    //사용자 관련된 모든 댓글 삭제
    void deleteAllByUser(User user);
}
