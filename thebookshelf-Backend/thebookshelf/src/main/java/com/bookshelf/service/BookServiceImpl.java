package com.bookshelf.service;

import java.util.List;
import java.util.Locale.Category;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookshelf.dto.UpdateBookDTO;
import com.bookshelf.entities.Book;
import com.bookshelf.exception.InvalidCredentialException;
import com.bookshelf.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService
{
	    @Autowired
	    private BookRepository bookRepository;

	    public List<Book> getAllBooks() {
	        return bookRepository.findAll();
	    }

	    public Book getBookById(Long id) {
	        return bookRepository.findById(id)
	                .orElseThrow(() -> new InvalidCredentialException("Book with id " + id + " not found"));
	    }

	    public Book addNewBook( Book newBook) {
	        return bookRepository.save(newBook);
	    }

	    public Book updateBook(Long id, UpdateBookDTO updateBookDTO) throws Exception {
	        if (updateBookDTO.getPrice() <= 0 || updateBookDTO.getAvailableQty() < 0) {
	            throw new Exception("Invalid price or quantity");
	        }
	        return bookRepository.findById(id)
	                .map(existingBook -> {
	                    // Only update the price and availableQty fields
	                    existingBook.setPrice(updateBookDTO.getPrice());
	                    existingBook.setAvailableQty(updateBookDTO.getAvailableQty());
	                    return bookRepository.save(existingBook);
	                })
	                .orElseThrow(() -> new InvalidCredentialException("Book with id " + id + " not found"));
	    }


	    
	    public List<Book> getBookByCategory(String category )
	    {
	    	return bookRepository.findBookByCategory(category);
	    }

	    public void deleteBook(Long id) {
	        Book book = bookRepository.findById(id)
	                .orElseThrow(() -> new InvalidCredentialException("Book with id " + id + " not found"));
	        bookRepository.delete(book);
	    }
	    
	
}
