//package com.daeta.board.repository;
//
//import com.daeta.board.dto.BoardRequestDto;
//import com.daeta.board.dto.CommentRequestDto;
//import com.daeta.board.entity.Board;
//import com.daeta.board.entity.Comment;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class BoardRepositoryTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Test
//    public void 게시물_저장_조회(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.USER)
//                .build();
//        userRepository.save(user);
//
//        BoardRequestDto boardRequestDto = new BoardRequestDto();
//        boardRequestDto.setTitle("제목");
//        boardRequestDto.setContents("내용");
//
//        //when
//       Board board = Board.of(boardRequestDto, user);
//       boardRepository.save(board);
//
//        //then
//        Board savedBoard = boardRepository.findById(board.getId()).orElse(null);
//
//        assertEquals("제목", savedBoard.getTitle());
//        assertEquals("내용", savedBoard.getDescription());
//        assertEquals(user, savedBoard.getUser());
//
//    }
//
//    @Test
//    public void 사용자_댓글_삭제(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.USER)
//                .build();
//        userRepository.save(user);
//
//        BoardRequestDto boardRequestDto = new BoardRequestDto();
//        boardRequestDto.setTitle("제목");
//        boardRequestDto.setContents("네용");
//
//
//        CommentRequestDto commentRequestDto = new CommentRequestDto();
//        commentRequestDto.setContents("댓글");
//        commentRequestDto.setParentCommentId(1L);
//
//        //when
//        Board board = Board.of(boardRequestDto, user);
//        boardRepository.save(board);
//
//        Comment comment = Comment.of(commentRequestDto, board,  user);
//        commentRepository.save(comment);
//
//
//        //then
//        //User와 관련된 댓글을 찾고, isPresent() 사용해 댓글이 존재하면 성공하는 걸로
//        Optional<Comment> savedComment = commentRepository.findByIdAndUser(comment.getId(), user);
//        assertTrue(savedComment.isPresent());
//
//
//    }
//
//}