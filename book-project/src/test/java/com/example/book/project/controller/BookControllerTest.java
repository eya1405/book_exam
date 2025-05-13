package com.example.book.project.controller;

import com.example.book.project.model.Book;
import com.example.book.project.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private BookRepository bookRepository;
    @Autowired private ObjectMapper objectMapper;

    private Book book;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        book = bookRepository.save(new Book("1234567890", "Test Book", "John Doe"));
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()));
    }

    @Test
    void shouldGetBookByIsbn() throws Exception {
        mockMvc.perform(get("/books/{isbn}", book.getIsbn()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    void shouldCreateBook() throws Exception {
        Book newBook = new Book("1111111111", "New Book", "Alice Smith");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(newBook.getIsbn()));

        assertThat(bookRepository.findById("1111111111")).isPresent();
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Book updated = new Book(book.getIsbn(), "Updated Title", "Updated Author");

        mockMvc.perform(put("/books/{isbn}", book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));

        assertThat(bookRepository.findById(book.getIsbn()).get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/{isbn}", book.getIsbn()))
                .andExpect(status().isOk());

        assertThat(bookRepository.findById(book.getIsbn())).isNotPresent();
    }
}
