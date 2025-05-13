package com.example.book.project.service;

import com.example.book.project.model.Book;
import com.example.book.project.repository.BookRepository;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"books", "book"})
public class BookServiceImpl {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable("books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "book", key = "#isbn")
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @CachePut(value = "book", key = "#book.isbn")
    @CacheEvict(value = "books", allEntries = true)
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @CachePut(value = "book", key = "#isbn")
    @CacheEvict(value = "books", allEntries = true)
    public Book updateBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Caching(evict = {
            @CacheEvict(value = "book", key = "#isbn"),
            @CacheEvict(value = "books", allEntries = true)
    })
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
