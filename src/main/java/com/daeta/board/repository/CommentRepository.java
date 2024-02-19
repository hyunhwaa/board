package com.daeta.board.repository;

import com.daeta.board.entity.Comment;
import com.daeta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //댓글 찾기, 값이 없을 수도 있으니까 Optional로 감쌈.
    Optional<Comment> findByIdAndUser(Long id, User user);

    //isbn으로 댓글 검색.
    List<Comment> findByBookIsbn(String isbn);

    //사용자 관련된 댓글 모두 삭제
    void deleteAllByUser(User user);

    //모든 댓글 가져오기
    List<Comment> findAll();
}
