package com.taller.bookstore.exception.custom;

public class AuthorHasBooksException extends RuntimeException {
    public AuthorHasBooksException(Long authorId) {
        super("Cannot delete author with id " + authorId + " because they have associated books");
    }
}
