package com.bookshelf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale.Category;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bookshelf.dto.UpdateBookDTO;
import com.bookshelf.entities.Book;
import com.bookshelf.exception.CategoryNotFoundException;
import com.bookshelf.service.BookService;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    private static final List<String> ALLOWED_CATEGORIES = Arrays.asList(
    	    "fiction", "mythology", "nonfiction", "historical fiction", "romance", "short stories"
    	);

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addNewBook(@RequestBody Book newBook) {
        Book createdBook = bookService.addNewBook(newBook);
        return ResponseEntity.status(201).body(createdBook);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody UpdateBookDTO updateBookDTO) {
        try {
            Book updatedBook = bookService.updateBook(id, updateBookDTO);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    
    @GetMapping("category/{category}")
    public List<Book> getBookByCategory(@PathVariable String category) {
        // Validate the category
        if (!ALLOWED_CATEGORIES.contains(category.toLowerCase())) {
            throw new CategoryNotFoundException("Invalid category: " + category);
        }

        // Proceed with fetching the books if the category is valid
        return bookService.getBookByCategory(category);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}