package com.taller.bookstore.repository;

import com.taller.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByCategoriesId(Long categoryId, Pageable pageable);

    boolean existsByAuthorId(Long authorId);
}
