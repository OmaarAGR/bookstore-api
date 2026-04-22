package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookResponse> findAll(Pageable pageable);
    Page<BookResponse> findByAuthor(Long authorId, Pageable pageable);
    Page<BookResponse> findByCategory(Long categoryId, Pageable pageable);
    BookResponse findById(Long id);
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
}
