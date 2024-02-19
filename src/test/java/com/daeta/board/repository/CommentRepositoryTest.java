//package com.daeta.board.repository;
//
//import com.daeta.board.dto.BookInfoDto;
//import com.daeta.board.dto.CommentRequestDto;
//import com.daeta.board.entity.Book;
//import com.daeta.board.entity.Comment;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import com.daeta.board.service.CommentService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
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
//public class CommentRepositoryTest {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void ISBN으로_댓글_검색(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("password")
//                .role(UserRole.USER)
//                .build();
//        userRepository.save(user);
//
//        Book book = Book.builder()
//                .isbn("123")
//                .title("책 제목")
//                .author("작가")
//                .image("image.jpg")
//                .discount("123")
//                .publisher("지은이")
//                .pubdate("20230920")
//                .description("설명")
//                .build();
//        bookRepository.save(book);
//
//        CommentRequestDto commentRequestDto = new CommentRequestDto();
//        commentRequestDto.setParentCommentId(1L);
//        commentRequestDto.setIsbn(book.getIsbn());
//        commentRequestDto.setContents("댓글내용");
//
//        //when
//
//
//
//
//
//
//
//
//    }
//
//
//
//}