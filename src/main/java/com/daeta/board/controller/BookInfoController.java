package com.daeta.board.controller;

import com.daeta.board.dto.BookInfoDto;
import com.daeta.board.entity.Likes;
import com.daeta.board.entity.User;
import com.daeta.board.security.UserDetailsImpl;
import com.daeta.board.service.BookInfoService;
import com.daeta.board.service.LikesService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookInfoController {
    private final BookInfoService bookInfoService;
    private final LikesService likesService;

    public BookInfoController(BookInfoService bookInfoService, LikesService likesService) {
        this.bookInfoService = bookInfoService;
        this.likesService = likesService;
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam String keyword,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "sim") String sort,
                             Model model) {

        Page<BookInfoDto> bookPage = bookInfoService.searchBooks(keyword, page, size, sort);

        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("bookList", bookPage.getContent());
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "book/bookSearch";
    }

    @GetMapping("/bookDetail/{isbn}")
    public String bookDetailPage(@PathVariable String isbn,
                                 @RequestParam(name = "commentId", required = false) Long commentId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){

        BookInfoDto bookDetail = bookInfoService.getBookDetail(isbn, commentId, userDetails.getUser());
        model.addAttribute("bookDetail", bookDetail);
        model.addAttribute("userDetails", userDetails);

        return "book/bookDetail";
    }

    //내 책장
    @GetMapping("/liked")
    public String getLikedPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        List<Likes> likedPosts = likesService.getLikedPosts(user);
        model.addAttribute("likedPosts", likedPosts);
        return "board/liked";

    }

}
