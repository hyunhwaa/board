//package com.daeta.board.repository;
//
//import com.daeta.board.dto.BoardRequestDto;
//import com.daeta.board.dto.CommentRequestDto;
//import com.daeta.board.entity.Board;
//import com.daeta.board.entity.Comment;
//import com.daeta.board.entity.Likes;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class LikesRepositoryTest {
//
//    @Autowired private UserRepository userRepository;
//    @Autowired private BoardRepository boardRepository;
//    @Autowired private CommentRepository commentRepository;
//    @Autowired private LikesRepository likesRepository;
//
//
//    @Test
//    public void 게시글_좋아요(){
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
//        Board board = Board.of(boardRequestDto, user);
//        boardRepository.save(board);
//
//        //when
//        Likes likes = Likes.builder()
//                .board(board)
//                .user(user)
//                .build();
//        likesRepository.save(likes);
//
//        //then
//        Optional<Likes> savedLikesOptional = likesRepository.findByBoardAndUser(board, user);
//        assertTrue(savedLikesOptional.isPresent());
//
//        Likes savedLikes = savedLikesOptional.get();
//        assertEquals(board, savedLikes.getBoard());
//        assertEquals(user, savedLikes.getUser());
//    }
//
//    @Test
//    public void 댓글_좋아요(){
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
//        Board board = Board.of(boardRequestDto, user);
//        boardRepository.save(board);
//
//        CommentRequestDto commentRequestDto = new CommentRequestDto();
//        commentRequestDto.setParentCommentId(1L);
//        commentRequestDto.setContents("내용");
//
//        Comment comment = Comment.of(commentRequestDto, board, user);
//        commentRepository.save(comment);
//
//        //when
//        Likes likes = Likes.builder()
//                .comment(comment)
//                .user(user)
//                .board(board)
//                .build();
//        likesRepository.save(likes);
//
//        //then
//        Optional<Likes> savedCommentOptional = likesRepository.findByCommentAndUser(comment, user);
//        assertTrue(savedCommentOptional.isPresent());
//
//        Likes savedComment = savedCommentOptional.get();
//        assertEquals(comment, savedComment.getComment());
//        assertEquals(user, savedComment.getUser());
//        assertEquals(board,savedComment.getBoard());
//    }
//
//}