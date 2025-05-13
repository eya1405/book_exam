package com.example.book.project.service;

import com.example.book.project.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book createBook(Book book);
    Book updateBook(String isbn, Book book);
    void deleteBook(String isbn);
}


