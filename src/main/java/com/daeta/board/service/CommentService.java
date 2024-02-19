package com.daeta.board.service;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.common.ResponseUtils;
import com.daeta.board.common.SuccessResponse;
import com.daeta.board.dto.CommentRequestDto;
import com.daeta.board.dto.CommentResponseDto;
import com.daeta.board.entity.Board;
import com.daeta.board.entity.Book;
import com.daeta.board.entity.Comment;
import com.daeta.board.entity.User;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.entity.enumSet.UserRole;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.repository.BoardRepository;
import com.daeta.board.repository.BookRepository;
import com.daeta.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final BoardRepository boardRepository;

    /**
     *댓글 작성
     */
    @Transactional
    public ApiResponseDto<CommentResponseDto> createComment(Long id, CommentRequestDto requestDto, User user){
        //선택한 게시글 DB 조회
        Optional<Board> findBoard = boardRepository.findById(id);
        if(findBoard.isEmpty()) {
            Optional<Book> findBook = bookRepository.findById(id);
            if (findBook.isEmpty()) {
                throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
            }

            return addBookComment(requestDto, findBook.get(), user);
        }else {
            return addBoardComment(requestDto, findBoard.get(), user);
        }
    }

    /**
     *댓글 수정
     */
    @Transactional
    public ApiResponseDto<CommentResponseDto> updateComment(Long id, CommentRequestDto requestDto, User user){
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        //댓글 작성자와 수정하려는 사용자 정보가 일치하는지. (관리자면 수정가능)
        Optional<Comment> found = commentRepository.findByIdAndUser(id, user);
        if(found.isEmpty() && user.getRole() == UserRole.USER){
            throw new RestApiException(ErrorType.NOT_WRITER);
        }

        //관리자이거나, 댓글 작성자와 수정하려는 사용자 정보가 일치하면 댓글 수정
        comment.get().update(requestDto, user);
        commentRepository.flush();      //responseDto에 modifiedAt 업데이트 해주기 위해 flush사용

        //ResponseEntity에 dto 담아서 리턴
        return ResponseUtils.ok(CommentResponseDto.from(comment.get()));
    }

    //댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long id){
        Optional<Comment> comment = commentRepository.findById(id);

        if(comment.isEmpty()){
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        Comment comments = comment.get();
        return CommentResponseDto.from(comments);
    }


    /**
     *댓글 삭제
     */
    public ApiResponseDto<SuccessResponse> deleteComment(Long id, User user){
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        Optional<Comment> found = commentRepository.findByIdAndUser(id, user);
        if(found.isEmpty() && user.getRole() == UserRole.USER){
            throw new RestApiException(ErrorType.NOT_WRITER);
        }
        commentRepository.deleteById(id);

        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "댓글 삭제 성공"));
    }

    private ApiResponseDto<CommentResponseDto> addBoardComment(CommentRequestDto requestDto, Board book, User user){
        Long parentCommentId = requestDto.getParentCommentId();
        Comment comment = Comment.of(requestDto, book, user);
        //parentComemntId 없다면 바로 저장.
        if(parentCommentId == null){
            commentRepository.save(comment);
            return ResponseUtils.ok(CommentResponseDto.from(comment));
        }
//        // parentComment가 있다면 childComment 추가
//        Comment parentComment = commentRepository.findById(parentCommentId)
//                .orElseThrow(() -> new RestApiException(ErrorType.NOT_FOUND_WRITING));
//        parentComment.addChildComment(comment);
//        commentRepository.save(comment);

        return ResponseUtils.ok(CommentResponseDto.from(comment));
    }

    private ApiResponseDto<CommentResponseDto> addBookComment(CommentRequestDto requestDto, Book board, User user){
        Long parentCommentId = requestDto.getParentCommentId();
        Comment comment = Comment.of(requestDto, board, user);
        //parentComemntId 없다면 바로 저장.
        if(parentCommentId == null){
            commentRepository.save(comment);
            return ResponseUtils.ok(CommentResponseDto.from(comment));
        }
        return ResponseUtils.ok(CommentResponseDto.from(comment));
    }

}
