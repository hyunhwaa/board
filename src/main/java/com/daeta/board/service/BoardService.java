package com.daeta.board.service;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.common.ResponseUtils;
import com.daeta.board.common.SuccessResponse;
import com.daeta.board.dto.BoardRequestDto;
import com.daeta.board.dto.BoardResponseDto;
import com.daeta.board.dto.CommentResponseDto;
import com.daeta.board.entity.Board;
import com.daeta.board.entity.Comment;
import com.daeta.board.entity.User;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.entity.enumSet.UserRole;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    /**
    게시글 작성
     */
    @Transactional
    public ApiResponseDto<BoardResponseDto> createPost(BoardRequestDto requestDto, User user){
        //작성 글 저장
        Board board = boardRepository.save(Board.of(requestDto, user));

        //BoardResponseDto 로 변환 후 responseEntity body에 담아 반환.
        return ResponseUtils.ok(BoardResponseDto.from(board));

    }

    /**
    선택된 게시글 조회
     */
    @Transactional(readOnly = true)
    public ApiResponseDto<BoardResponseDto> getPost(Long id){
        //id에 해당하는 게시글있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){    //게시물 없으면
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        //댓글 리스트 작성일자 기준 내림차순 정렬
        board.get()
                .getCommentList()
                .sort(Comparator.comparing(Comment::getModifiedAt)
                        .reversed());


        //댓글 리스트
        List<CommentResponseDto> commentList = board.get()
                .getCommentList()
                .stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());

        return ResponseUtils.ok(BoardResponseDto.from(board.get(), commentList));

    }

    /**
    게시글 수정
     */
    @Transactional
    public ApiResponseDto<BoardResponseDto> updatePost(Long id, BoardRequestDto boardRequestDto, User user){
        //게시글 DB있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()){
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        //선택한 게시글의 작성자와 토큰에서 가져온 사용자 정보가 일치하는지 확인.(만약 관리자라면 수정 가능)
        Optional<Board> found = boardRepository.findByIdAndUser(id, user);
        if(found.isEmpty() && user.getRole() == UserRole.USER){     //일치하는 게시글 없으면
            throw new RestApiException(ErrorType.NOT_WRITER);
        }

        //게시글 id와 사용자 정보가 일치한다면 게시글 수정
        board.get().update(boardRequestDto, user);
        boardRepository.flush();        //responseDto에 modifiedAt 업데이트 해주기 위해 flush 사용

        return ResponseUtils.ok(BoardResponseDto.from(board.get()));
    }


    /**
    게시글 삭제
     */
    public ApiResponseDto<SuccessResponse> deletePost(Long id, User user){
        //선택한 게시글 DB에 있는지 확인
        Optional<Board> found = boardRepository.findById(id);
        if(found.isEmpty()){
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }

        //선택한 게시글의 작성자와 토큰에서 가져온 사용자 정보가 일치하는지 (관리자면 삭제 가능)
        Optional<Board> board = boardRepository.findByIdAndUser(id, user);
        if(board.isEmpty() && user.getRole() == UserRole.USER){
            throw new RestApiException(ErrorType.NOT_WRITER);
        }

        //게시글 id와 사용자 정보가 일치한다면 게시글 삭제
        boardRepository.deleteById(id);
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "게시글 삭제 성공"));

    }


    /**
    게시글 전체 목록 조회
     */
    @Transactional(readOnly = true)
    public ApiResponseDto<List<BoardResponseDto>> getPosts(){
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> responseDtoList = new ArrayList<>();

        for(Board board : boardList){
            //댓글 작성일자 기준 내림차순 정렬
            board.getCommentList()
                    .sort(Comparator.comparing(Comment::getModifiedAt)
                            .reversed());
            //대댓글은 제외
            List<CommentResponseDto> commentList = new ArrayList<>();
            for(Comment comment : board.getCommentList()){
                if(comment.getParentCommentId() == null){
                    commentList.add(CommentResponseDto.from(comment));
                }
            }
            //List<BoardResponseDto>로 만들기 위해 board를 BoardResponseDto로 만들고, list에 dto를 하나씩 넣음
            responseDtoList.add(BoardResponseDto.from(board, commentList));
        }
        return ResponseUtils.ok(responseDtoList);
    }


}
