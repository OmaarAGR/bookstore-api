package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.entity.Author;
import com.taller.bookstore.entity.Book;
import com.taller.bookstore.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;

    public BookMapper(AuthorMapper authorMapper, CategoryMapper categoryMapper) {
        this.authorMapper = authorMapper;
        this.categoryMapper = categoryMapper;
    }

    public Book toEntity(BookRequest request, Author author, List<Category> categories) {
        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .stock(request.getStock())
                .author(author)
                .categories(categories)
                .build();
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stock(book.getStock())
                .author(authorMapper.toResponse(book.getAuthor()))
                .categories(book.getCategories() == null ? List.of() :
                        book.getCategories().stream()
                                .map(categoryMapper::toResponse)
                                .collect(Collectors.toList()))
                .build();
    }
}
