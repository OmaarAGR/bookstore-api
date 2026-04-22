package com.taller.bookstore.controller;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@Tag(name = "Authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "List all authors")
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(authorService.findAll(), "Autores obtenidos exitosamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID")
    public ResponseEntity<ApiResponse<AuthorResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(authorService.findById(id), "Autor obtenido exitosamente"));
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "List books by author")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> findBooks(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(authorService.findBooksByAuthor(id, pageable), "Libros del autor obtenidos exitosamente"));
    }

    @PostMapping
    @Operation(summary = "Create a new author", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<AuthorResponse>> create(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse created = authorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Autor creado exitosamente", 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<AuthorResponse>> update(@PathVariable Long id,
                                                              @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authorService.update(id, request), "Autor actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Autor eliminado exitosamente"));
    }
}
