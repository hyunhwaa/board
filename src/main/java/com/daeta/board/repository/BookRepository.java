package com.daeta.board.repository;

import com.daeta.board.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findById(Long id);

}
