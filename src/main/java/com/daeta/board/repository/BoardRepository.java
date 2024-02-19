package com.daeta.board.repository;


import com.daeta.board.entity.Board;
import com.daeta.board.entity.Book;
import com.daeta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    //게시글 검색
    Optional<Board> findByIdAndUser(Long id, User user);

    //모든 게시글 작성 날짜 기준으로 내림차순 정렬
    List<Board> findAllByOrderByModifiedAtDesc();

    //사용자 관련된 모든 게시글 삭제
    void deleteAllByUser(User user);

    Optional<Board> findById(Long id);
}
