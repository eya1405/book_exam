package com.example.book.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookProjectApplication.class, args);
	}
}

