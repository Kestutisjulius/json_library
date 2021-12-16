package com.example.Library.repository;

import com.example.Library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    void deleteBookById(Long id);
    List<Book>findAllByAuthor(String author);
    List<Book>findAllByCategory(String category);
    List<Book>findAllByLanguage(String language);
    List<Book>findByISBN(String isbn);
    List<Book>findByName(String name);

}
