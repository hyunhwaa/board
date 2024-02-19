package com.daeta.board.common;

import com.daeta.board.dto.BookInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<BookInfoDto> items;
    private int total;
}
