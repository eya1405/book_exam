package com.example.book.project.service;

import com.example.book.project.model.Book;
import com.example.book.project.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    public BookServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book("123", "Title1", "Author1"),
                new Book("456", "Title2", "Author2")
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getIsbn());
    }
}
