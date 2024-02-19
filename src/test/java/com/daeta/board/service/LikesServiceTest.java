//package com.daeta.board.service;
//
//import com.daeta.board.common.ApiResponseDto;
//import com.daeta.board.dto.BoardRequestDto;
//import com.daeta.board.dto.BoardResponseDto;
//import com.daeta.board.dto.CommentRequestDto;
//import com.daeta.board.dto.CommentResponseDto;
//import com.daeta.board.entity.Board;
//import com.daeta.board.entity.Comment;
//import com.daeta.board.entity.Likes;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import com.daeta.board.repository.BoardRepository;
//import com.daeta.board.repository.CommentRepository;
//import com.daeta.board.repository.LikesRepository;
//import com.daeta.board.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class LikesServiceTest {
//    @Autowired private LikesService likesService;
//    @Autowired private BoardService boardService;
//    @Autowired private UserRepository userRepository;
//    @Autowired private BoardRepository boardRepository;
//    @Autowired private CommentRepository commentRepository;
//    @Autowired private LikesRepository likesRepository;
//    @Autowired private EntityManager em;
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
//
//
//        //when
//        ApiResponseDto<BoardResponseDto> response1 = likesService.likePost(board.getId(), user);
//        System.out.println("response1===" + response1.getResponse().getLikeCount());
//        em.refresh(board);
//
//        ApiResponseDto<BoardResponseDto> response2 = likesService.likePost(board.getId(), user);
//        System.out.println("response2.getResponse().getLikeCount() = " + response2.getResponse().getLikeCount());
//
//        //then
//
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
//
//        CommentRequestDto parentCommentRequestDto = new CommentRequestDto();
//        parentCommentRequestDto.setParentCommentId(null);
//        parentCommentRequestDto.setContents("부모댓글");
//        Comment parentComment = Comment.of(parentCommentRequestDto, board, user);
//        commentRepository.save(parentComment);
//
//        //when
//        ApiResponseDto<CommentResponseDto> response = likesService.likeComment(parentComment.getId(), user);
//
//        //then
//        assertTrue(response.isSuccess());
//        System.out.println("response.getResponse().getLikeCount() = " + response.getResponse().getLikeCount());
//
//         }
//
//}