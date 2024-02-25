package com.daeta.board.controller;

import com.daeta.board.dto.BookInfoDto;
import com.daeta.board.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final BookInfoService bookInfoService;
    @Autowired
    public ApiController(BookInfoService apiService) {
        this.bookInfoService = apiService;
    }
    @GetMapping("/search")
    public ResponseEntity<List<BookInfoDto>> searchPage(@RequestParam String keyword,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "sim") String sort) {

       long startTime = System.currentTimeMillis();

        Page<BookInfoDto> bookPage = bookInfoService.searchBooks(keyword, page, size, sort);
        List<BookInfoDto> bookList = bookPage.getContent();


        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("검색 요청 소요 시간:" + duration + "ms");

        //HTTP 상태코드 200 OK 와 함께 검색된 책 목록 반환
        return ResponseEntity.ok(bookList);
    }

}