package com.taller.bookstore.exception.custom;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String bookTitle, int requested, int available) {
        super("Insufficient stock for book '" + bookTitle + "': requested " + requested + ", available " + available);
    }
}
