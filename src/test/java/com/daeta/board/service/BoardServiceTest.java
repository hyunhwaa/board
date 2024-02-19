//package com.daeta.board.service;
//
//import com.daeta.board.common.ApiResponseDto;
//import com.daeta.board.common.SuccessResponse;
//import com.daeta.board.dto.BoardRequestDto;
//import com.daeta.board.dto.BoardResponseDto;
//import com.daeta.board.dto.CommentRequestDto;
//import com.daeta.board.dto.CommentResponseDto;
//import com.daeta.board.entity.Board;
//import com.daeta.board.entity.Comment;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import com.daeta.board.repository.BoardRepository;
//import com.daeta.board.repository.CommentRepository;
//import com.daeta.board.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class BoardServiceTest {
//
//    @Autowired
//    private BoardService boardService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Test
//    public void 게시글_작성() {
//        //given
//        BoardRequestDto boardRequestDto = new BoardRequestDto();
//        boardRequestDto.setTitle("제목");
//        boardRequestDto.setContents("내용");
//
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.USER)
//                .build();
//        userRepository.save(user);
//
//        //when
//        ApiResponseDto<BoardResponseDto> response = boardService.createPost(boardRequestDto, user);
//
//        //then
//        assertTrue(response.isSuccess());       //성공??
//
//        BoardResponseDto boardResponseDto = response.getResponse();
//
//        assertEquals("제목", boardResponseDto.getTitle());
//        assertEquals("내용", boardResponseDto.getDescription());
//        assertEquals("test", boardResponseDto.getUsername());
//
//    }
//
//    @Test
//    public void 선택_게시글_조회() {
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
//        CommentRequestDto commentRequestDto = new CommentRequestDto();
//        commentRequestDto.setContents("댓글내용");
//        commentRequestDto.setParentCommentId(1L);
//
//        Comment comment = Comment.of(commentRequestDto, board, user);
//        commentRepository.save(comment);
//
//        //when
//        ApiResponseDto<BoardResponseDto> response = boardService.getPost(board.getId());
//
//        //then
//        assertEquals("제목", response.getResponse().getTitle());
//        assertEquals("내용", response.getResponse().getDescription());
//        assertEquals("test", response.getResponse().getUsername());
//
//        List<CommentResponseDto> sorted = response.getResponse().getCommentList();
//        assertEquals("댓글내용", sorted.get(0).getContents());
//
//
//        //게시글 예외 터지는지
//        long nonBoardId = 99L;
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> boardService.getPost(nonBoardId));
//
//        assertEquals("게시글 없음 : " + nonBoardId, exception.getMessage());
//
//    }
//
//    @Test
//    public void 선택_게시글_수정(){
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
//        BoardRequestDto updateRequestDto = new BoardRequestDto();
//        updateRequestDto.setTitle("수정된제목");
//        updateRequestDto.setContents("수정된내용");
//
//        //when
//        ApiResponseDto<BoardResponseDto> response = boardService.updatePost(board.getId(), updateRequestDto, user);
//
//        //게시글이 DB에 없을때
//        long nonBoardId = 99;
//        BoardRequestDto nonBoardRequestDto = new BoardRequestDto();
//        nonBoardRequestDto.setTitle("새로운제목");
//        nonBoardRequestDto.setContents("새로운내용");
//
//        RuntimeException exception = assertThrows(RuntimeException.class, ()-> boardService.updatePost(nonBoardId, nonBoardRequestDto, user));
//
//        //then
//        assertEquals("수정된제목", response.getResponse().getTitle());
//        assertEquals("수정된내용", response.getResponse().getDescription());
//
//        assertEquals("게시글 없음 : " + nonBoardId, exception.getMessage());
//
//    }
//
//    @Test
//    public void 관리자_수정(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.ADMIN)
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
//        BoardRequestDto updateRequestDto = new BoardRequestDto();
//        updateRequestDto.setTitle("수정된제목");
//        updateRequestDto.setContents("수정된내용");
//
//        //when
//        ApiResponseDto<BoardResponseDto> response = boardService.updatePost(board.getId(), updateRequestDto, user);
//
//        //then
//        assertEquals("수정된제목", response.getResponse().getTitle());
//        assertEquals("수정된내용", response.getResponse().getDescription());
//
//    }
//
//    @Test
//    public void 게시글_삭제(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.ADMIN)
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
//        ApiResponseDto<SuccessResponse> response = boardService.deletePost(board.getId(), user);
//
//        //then
//        assertTrue(response.isSuccess());
//        assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
//        assertEquals("게시글 삭제 성공", response.getResponse().getMessage());
//
//        List<Board> allBoard = boardRepository.findAll();
//        System.out.println("===== 현재 게시글 수 : " +  allBoard.size());
//    }
//
//    @Test
//    public void 관리자_삭제(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("1234")
//                .role(UserRole.ADMIN)
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
//        ApiResponseDto<SuccessResponse> response = boardService.deletePost(board.getId(), user);
//
//        //then
//        assertTrue(response.isSuccess());
//
//        List<Board> all = boardRepository.findAll();
//        System.out.println("==== 게시글 수 : " + all.size());
//
//    }
//
//    @Test
//    public void 게시글_전체_조회(){
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
//        commentRequestDto.setParentCommentId(board.getId());
//        commentRequestDto.setContents("댓글");
//        Comment comment = Comment.of(commentRequestDto,board,user);
//        commentRepository.save(comment);
//
//        //when
//        ApiResponseDto<List<BoardResponseDto>> response = boardService.getPosts();
//
//        //then
//        assertTrue(response.isSuccess());
//
//
//        List<BoardResponseDto> boardResponseDtoList = response.getResponse();
//        BoardResponseDto first = boardResponseDtoList.get(0);
//        assertEquals("제목", first.getTitle());
//        System.out.println("댓글 조회---- " + commentRepository.findAll().size());
//        System.out.println("======" + commentRepository.findAll().get(0));
//        assertEquals("댓글", commentRepository.findAll().get(0).getContents());
//    }
//
//}