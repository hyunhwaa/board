//package com.daeta.board.service;
//
//import com.daeta.board.common.ApiResponseDto;
//import com.daeta.board.dto.BookInfoDto;
//import com.daeta.board.dto.CommentRequestDto;
//import com.daeta.board.dto.CommentResponseDto;
//import com.daeta.board.entity.Book;
//import com.daeta.board.entity.Comment;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import com.daeta.board.repository.BookRepository;
//import com.daeta.board.repository.CommentRepository;
//import com.daeta.board.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class CommentServiceTest {
//
//    @Autowired private UserRepository userRepository;
//    @Autowired private BookRepository bookRepository;
//    @Autowired private CommentRepository commentRepository;
//    @Autowired private CommentService commentService;
//
//    @Test
//    public void 댓글_작성(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.USER)
//                .build();
//        userRepository.save(user);
//
//
//
//        BookInfoDto bookInfoDto = new BookInfoDto();
//        bookInfoDto.setTitle("책 제목");
//        bookInfoDto.setAuthor("작가");
//        bookInfoDto.setImage("이미지 경로");
//        bookInfoDto.setDiscount("할인 정보");
//        bookInfoDto.setPublisher("출판사");
//        bookInfoDto.setPubdate("출판일");
//        bookInfoDto.setIsbn("ISBN 번호");
//        bookInfoDto.setDescription("책 설명");
//        bookInfoDto.setLikeCount(10);
//
//        CommentRequestDto commentRequestDto = new CommentRequestDto();
//        commentRequestDto.setParentCommentId(1L);
//        commentRequestDto.setContents("댓글내용");
//        commentRequestDto.setIsbn(bookInfoDto.getIsbn());
//
//        bookInfoDto.setCommentList();
//
//
//        //when
//        ApiResponseDto<CommentResponseDto> response = commentService.createComment(bookInfoDto.getIsbn(), commentRequestDto, user);
//
//        //then
//        List<Comment> comments = commentRepository.findByBookIsbn(bookInfoDto.getIsbn());
//        assertFalse(comments.isEmpty());
//
//        System.out.println("댓글 보여줘라 " +comments);
//    }
//
////    @Test
////    public void 댓글_수정(){
////        //given
////        User user = User.builder()
////                .username("test")
////                .password("1234")
////                .role(UserRole.USER)
////                .build();
////        userRepository.save(user);
////
////        BoardRequestDto boardRequestDto = new BoardRequestDto();
////        boardRequestDto.setTitle("제목");
////        boardRequestDto.setContents("내용");
////
////        Board board = Board.of(boardRequestDto, user);
////        boardRepository.save(board);
////
////
////        CommentRequestDto parentCommentRequestDto = new CommentRequestDto();
////        parentCommentRequestDto.setParentCommentId(null);
////        parentCommentRequestDto.setContents("부모댓글");
////        Comment parentComment = Comment.of(parentCommentRequestDto, board, user);
////        commentRepository.save(parentComment);
////
////        CommentRequestDto commentRequestDto = new CommentRequestDto();
////        commentRequestDto.setParentCommentId(parentComment.getId());
////        commentRequestDto.setContents("댓글");
////
////        Comment comment = Comment.of(commentRequestDto, board, user);
////        commentRepository.save(comment);
////
////        //수정
////        CommentRequestDto updateComment = new CommentRequestDto();
////        updateComment.setContents("수정");
////
////
////        //when
////        ApiResponseDto<CommentResponseDto> response = commentService.updateComment(comment.getId(), updateComment, user);
////
////        //then
////        assertTrue(response.isSuccess());
////        assertEquals(updateComment.getContents(), comment.getContents());
////    }
////
////    @Test
////    public void 댓글_삭제(){
////        //given
////        User user = User.builder()
////                .username("test")
////                .password("1234")
////                .role(UserRole.USER)
////                .build();
////        userRepository.save(user);
////
////        BoardRequestDto boardRequestDto = new BoardRequestDto();
////        boardRequestDto.setTitle("제목");
////        boardRequestDto.setContents("내용");
////
////        Board board = Board.of(boardRequestDto, user);
////        boardRepository.save(board);
////
////
////        CommentRequestDto parentCommentRequestDto = new CommentRequestDto();
////        parentCommentRequestDto.setParentCommentId(null);
////        parentCommentRequestDto.setContents("부모댓글");
////        Comment parentComment = Comment.of(parentCommentRequestDto, board, user);
////        commentRepository.save(parentComment);
////
////        CommentRequestDto commentRequestDto = new CommentRequestDto();
////        commentRequestDto.setParentCommentId(parentComment.getId());
////        commentRequestDto.setContents("댓글");
////
////        Comment comment = Comment.of(commentRequestDto, board, user);
////        commentRepository.save(comment);
////
////        //when
////        ApiResponseDto<SuccessResponse> response = commentService.deleteComment(comment.getId(), user);
////
////        assertTrue(response.isSuccess());
////        assertEquals("댓글 삭제 성공", response.getResponse().getMessage());
////    }
//
//
//}