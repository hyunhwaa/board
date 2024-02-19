package com.daeta.board.service;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.common.ResponseUtils;
import com.daeta.board.dto.BoardResponseDto;
import com.daeta.board.dto.BookInfoDto;
import com.daeta.board.dto.CommentResponseDto;
import com.daeta.board.entity.*;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.repository.BoardRepository;
import com.daeta.board.repository.BookRepository;
import com.daeta.board.repository.CommentRepository;
import com.daeta.board.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final BoardRepository boardRepository;

    /**
     * 게시글 좋아요.
     */
    public ApiResponseDto<?> likePost(Long id, User user){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){ // 책이 존재하는 경우
            Book foundBook = book.get();
            Optional<Likes> found = likesRepository.findByBookAndUser(foundBook, user);
            if (found.isEmpty()){
                Likes likes = Likes.of(foundBook, user);
                likesRepository.save(likes);
            } else {
                likesRepository.delete(found.get());
                likesRepository.flush();
            }
            return ApiResponseDto.builder().success(true).response(BookInfoDto.from(foundBook)).build();
        } else {
            Optional<Board> board = boardRepository.findById(id);
            if(board.isPresent()){ // 게시글이 존재하는 경우
                Board foundBoard = board.get();
                Optional<Likes> found = likesRepository.findByBoardAndUser(foundBoard, user);
                if(found.isEmpty()){
                    Likes likes = Likes.of(foundBoard, user);
                    likesRepository.save(likes);
                } else {
                    likesRepository.delete(found.get());
                    likesRepository.flush();
                }
                return ApiResponseDto.builder().success(true).response(BoardResponseDto.from(foundBoard)).build();
            } else {
                // 책과 게시글이 모두 존재하지 않는 경우에 대한 처리
                throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
            }
        }
    }
    /**
     * 댓글 좋아요
     */
    public ApiResponseDto<CommentResponseDto> likeComment(Long id, User user){
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        String isbn = comment.get().getBook().getIsbn();

        //전에 좋아요 누른적 있는지 확인
        Optional<Likes> found = likesRepository.findByCommentAndUser(comment.get(), user);
        if(found.isEmpty()){
            Likes likes = Likes.of(comment.get(), user);
            likesRepository.save(likes);
        } else {
          likesRepository.delete(found.get());
          likesRepository.flush();
        }
        return ResponseUtils.ok(CommentResponseDto.from(comment.get()));

    }

    //내 책장
    public List<Likes> getLikedPosts(User user){
        return likesRepository.findByUser(user);
    }
}
