package com.daeta.board.controller;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.common.ErrorResponse;
import com.daeta.board.common.SuccessResponse;
import com.daeta.board.dto.BoardRequestDto;
import com.daeta.board.dto.BoardResponseDto;
import com.daeta.board.entity.Book;
import com.daeta.board.security.UserDetailsImpl;
import com.daeta.board.service.BoardService;
import com.daeta.board.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequestMapping("/board")
public class BoardController {
    private final BookInfoService bookInfoService;
    private final BoardService boardService;

    @Autowired
    public BoardController(BookInfoService bookService, BoardService boardService) {
        this.bookInfoService = bookService;
        this.boardService = boardService;
    }

    @GetMapping("/index")
    public String mainForm(Authentication authentication, Model model){
        // 세션에 로그인 정보가 있는지 확인
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            // 세션에 로그인한 사용자 정보가 있음
            // 사용자 정보는 authentication.getPrincipal()에서 얻을 수 있음
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            System.out.println("세션에 로그인 정보가 있습니다. 사용자명: " + userDetails.getUsername());
        } else {
            // 세션에 로그인 정보가 없음
            System.out.println("세션에 로그인 정보가 없습니다.");
        }
        //랜덤 도서 목록
        List<Book> randomBooks = bookInfoService.getRandomBooks(4);
        model.addAttribute("randomBooks", randomBooks);

        //리뷰 순
        List<Book> topBooksByComments = bookInfoService.getTopBooksByComments(4);
        model.addAttribute("topBooksByComments", topBooksByComments);

        //좋아요 순
        List<Book> topBooksByLikes = bookInfoService.getTopBooksByLikes(4);
        model.addAttribute("topBooksByLikes", topBooksByLikes);

        return "index";
    }
    //게시글 전체 목록 조회
    @GetMapping("/debate")
    public String getPosts(Model model){
        ApiResponseDto<List<BoardResponseDto>> response = boardService.getPosts();
        if(response.isSuccess()){
            List<BoardResponseDto> postList = response.getResponse();
            model.addAttribute("postList", postList);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");
            List<String> formattedModifiedAtList = new ArrayList<>();
            for(BoardResponseDto post: postList){
                if(post.getModifiedAt() != null){
                    String formattedModifiedAt = post.getModifiedAt().format(formatter);
                    formattedModifiedAtList.add(formattedModifiedAt);
                }
            }
            model.addAttribute("formattedModifiedAtList", formattedModifiedAtList);
        }else {
            ErrorResponse error = response.getError();
        }
        return "board/debate";
    }

    //선택된 게시글 조회
    @GetMapping("/debate/post/{postId}")
    public String postDetails(@PathVariable("postId") Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              Model model){
        ApiResponseDto<BoardResponseDto> findPost = boardService.getPost(id);
        BoardResponseDto post = findPost.getResponse();
        model.addAttribute("post", post);
        model.addAttribute("userDetails", userDetails);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");
        model.addAttribute("modifiedAt", post.getModifiedAt().format(formatter));
        model.addAttribute("createAt", post.getCreateAt().format(formatter));

        return "board/postDetail";
    }
    //게시글 작성
    @GetMapping("/debate/create_post")
    public String createPost(){
        return "board/createPost";
    }
    @PostMapping("/debate/create_post")
    public String createPost(@ModelAttribute("requestDto")BoardRequestDto requestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                             Model model){
        ApiResponseDto<BoardResponseDto> post = boardService.createPost(requestDto, userDetails.getUser());
        model.addAttribute("post", post);

        return "redirect:/board/debate";
    }
    //게시글 수정
    @GetMapping("/debate/post/update/{postId}")
    public String getUpdatePost(@PathVariable("postId") Long id, Model model){
        ApiResponseDto<BoardResponseDto> post = boardService.getPost(id);
        model.addAttribute("post", post.getResponse());
        return "board/updatePost";
    }
    @PostMapping("/debate/post/{postId}")
    public String updatePost(@PathVariable("postId") Long id,
                             @ModelAttribute("boardRequestDto") BoardRequestDto requestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        ApiResponseDto<BoardResponseDto> updatePost = boardService.updatePost(id,requestDto, userDetails.getUser());
        model.addAttribute("updatePost", updatePost);

        return "redirect:/board/debate";
    }
    //게시글 삭제
    @DeleteMapping("/debate/post/{postId}")
    public String deletePost(@PathVariable("postId") Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        ApiResponseDto<SuccessResponse> deletePost = boardService.deletePost(id, userDetails.getUser());
        return "board/debate";
    }
}