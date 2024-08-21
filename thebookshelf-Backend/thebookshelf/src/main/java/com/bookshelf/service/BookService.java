package com.bookshelf.service;

import java.util.List;
import java.util.Locale.Category;

import javax.validation.Valid;

import com.bookshelf.dto.UpdateBookDTO;
import com.bookshelf.entities.Book;

public interface BookService {

   public List<Book> getAllBooks();

   public Book getBookById(Long id);

   public Book addNewBook(Book newbook);

   public Book updateBook(Long id, UpdateBookDTO updateBookDTO) throws Exception;

   public void deleteBook(Long id);
   
   public List<Book> getBookByCategory(String category );
}
