package com.taller.bookstore.controller;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@Tag(name = "Books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "List all books with pagination and optional filters")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> findAll(
            Pageable pageable,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long categoryId) {

        Page<BookResponse> books;
        if (authorId != null) {
            books = bookService.findByAuthor(authorId, pageable);
        } else if (categoryId != null) {
            books = bookService.findByCategory(categoryId, pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        return ResponseEntity.ok(ApiResponse.success(books, "Libros obtenidos exitosamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    public ResponseEntity<ApiResponse<BookResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(bookService.findById(id), "Libro obtenido exitosamente"));
    }

    @PostMapping
    @Operation(summary = "Create a new book", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<BookResponse>> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Libro creado exitosamente", 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<BookResponse>> update(@PathVariable Long id,
                                                            @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success(bookService.update(id, request), "Libro actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Libro eliminado exitosamente"));
    }
}
