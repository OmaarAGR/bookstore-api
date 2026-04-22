package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookRequest {

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String isbn;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private Long authorId;

    private List<Long> categoryIds;
}
