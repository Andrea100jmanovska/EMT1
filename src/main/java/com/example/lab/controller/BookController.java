package com.example.lab.controller;

import com.example.lab.entity.BookEntity;
import com.example.lab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/products")
public class BookController{

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    private ResponseEntity<BookEntity> findAll() {
        return this.bookService.listAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> findBookById(@PathVariable Long id) {
        return this.bookService.findBookById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<BookEntity> saveBook(BookEntity) {
        return this.bookService.saveBook()
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BookEntity> saveBook(@PathVariable Long id) {
        return this.bookService.editBook(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        if(this.bookService.findBookById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
