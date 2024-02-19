package com.daeta.board.service;

import com.daeta.board.common.ApiResponse;
import com.daeta.board.dto.BookInfoDto;
import com.daeta.board.dto.CommentResponseDto;
import com.daeta.board.entity.Book;
import com.daeta.board.entity.Comment;
import com.daeta.board.entity.Likes;
import com.daeta.board.entity.User;
import com.daeta.board.repository.BookRepository;
import com.daeta.board.repository.CommentRepository;
import com.daeta.board.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookInfoService {

    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookInfoService(RestTemplate restTemplate, BookRepository bookRepository, LikesRepository likesRepository, CommentRepository commentRepository) {
        this.restTemplate = restTemplate;
        this.bookRepository = bookRepository;
        this.likesRepository = likesRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 책 검색
     */
    public Page<BookInfoDto> searchBooks(String keyword, int page, int size, String sort) {

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 요청 페이지 시작 인덱스
        int startIndex = (page - 1) * size + 1;
        // 최대 결과 개수 제한
        int display = 60;

        String apiUrl = "https://openapi.naver.com/v1/search/book?query=" + keyword + "&start=" + startIndex + "&display=" + display + "&sort=" + sort;

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                ApiResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<BookInfoDto> bookList = response.getBody().getItems();
            // 총 검색 결과 수
            int totalResults = response.getBody().getTotal();
            // 가져온 결과가 60개 이상이라면 결과를 60개로 제한
            if (totalResults > 60) {
                bookList = bookList.subList(0, 60);
                totalResults = 60;
            }

            int endIndex = Math.min(startIndex + size, totalResults);
            // 현재 페이지에 표시할 책 목록
            List<BookInfoDto> currentPageBooks = bookList.subList(startIndex, endIndex);
            for(BookInfoDto bookInfoDto : currentPageBooks) {
                //isbn으로 이미 db에 책 있는지 확인.
                Optional<Book> existingBookOptional = bookRepository.findByIsbn(bookInfoDto.getIsbn());
                if (existingBookOptional.isEmpty()) {
                    Book book = Book.of(bookInfoDto);
                    bookRepository.save(book);
                    bookInfoDto.setId(book.getId());
                }else{
                    bookInfoDto.setId(existingBookOptional.get().getId());
                }
            }
            // 책 목록을 페이지로 변환하여 반환
            return new PageImpl<>(currentPageBooks, PageRequest.of(page - 1, size), totalResults);
        } else {
            return Page.empty();
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "05yD1w2OeU24HFfyVAU2");
        headers.set("X-Naver-Client-Secret", "SLfa8DnFEP");
        return headers;
    }

    /**
     * 책 상세 정보
     */
    public BookInfoDto getBookDetail(String isbn, Long commentId, User user){

        Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
        BookInfoDto bookInfoDto = new BookInfoDto();

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            bookInfoDto = BookInfoDto.from(book);

            Optional<Comment> comment = commentRepository.findByIdAndUser(commentId, user);
            BookInfoDto finalBookInfoDto = bookInfoDto;
            comment.ifPresent(c -> finalBookInfoDto.setCommentList(Collections.singletonList(CommentResponseDto.from(c))));
        }

        return bookInfoDto;
    }

    /**
     * 추천 도서
     */
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }
    public List<Likes> getAllLikes(){
        return likesRepository.findAll();
    }

    //리뷰 순
    public Map<String, Integer> getCommentCounts(List<Comment> comments){
        Map<String, Integer> commentCounts = new HashMap<>();
        for(Comment comment: comments){
            Book book = comment.getBook();
            if(book!=null){
                String isbn = book.getIsbn();
                commentCounts.put(isbn, commentCounts.getOrDefault(isbn, 0) + 1);
            }
        }
        return commentCounts;
    }

    //좋아요 순
    public Map<String, Integer> getLikesCounts(List<Book> books){
        Map<String, Integer> likesCounts = new HashMap<>();
        for(Book book : books){
            if(book != null) {
                List<Likes> likesList = book.getLikesList();
                int likeCount = 0;
                for(Likes likes : likesList){
                    likeCount++;
                }
                likesCounts.put(book.getIsbn(), likeCount);
            }
        }
        return likesCounts;
    }

    //Top4
    public List<Book> getTopNBooks(List<String> topBookIsbn, int n){
        List<Book> topBooks = new ArrayList<>();
        for(String isbn : topBookIsbn){
            Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
            bookOptional.ifPresent(topBooks::add);
            if(topBooks.size() == n){
                break;
            }
        }
        return topBooks;
    }
    public List<Book> getRandomBooks(int count) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> randomBooks = new ArrayList<>();
        Random random = new Random();
        while (randomBooks.size() < count && randomBooks.size() < bookList.size()) {
            int randomIndex = random.nextInt(bookList.size());
            Book book = bookList.get(randomIndex);
            if (!randomBooks.contains(book)) {
                randomBooks.add(book);
            }
        }
        return randomBooks;
    }

    public List<Book> getTopBooksByComments(int count) {
        List<Comment> commentList = commentRepository.findAll();
        Map<String, Integer> commentCounts = getCommentCounts(commentList);
        List<String> topBooksIsbnByComments = commentCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return getTopNBooks(topBooksIsbnByComments, count);
    }

    public List<Book> getTopBooksByLikes(int count) {
        List<Likes> likesList = likesRepository.findAll();
        Map<String, Integer> likesCounts = likesList.stream()
                .filter(likes -> likes.getBook() != null) // book 속성이 null이 아닌 경우에만 처리
                .collect(Collectors.groupingBy(
                        likes -> likes.getBook().getIsbn(),
                        Collectors.reducing(0, likes -> 1, Integer::sum)
                ));

        List<String> topBooksByLikes = likesCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return getTopNBooks(topBooksByLikes, count);
    }

}