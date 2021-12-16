package com.example.Library.service;

import com.example.Library.model.Book;
import com.example.Library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookService{

    @Autowired
    private final BookRepository bookRepository;

    public List<Book> findAllBooks() {return bookRepository.findAll();}

    public void addBook(Book book) {
        book.setISBN(UUID.randomUUID().toString());
        book.setGUID(UUID.randomUUID().toString());
        bookRepository.save(book);
        log.info("Book was added !");
    }

    public Book findBookByISBN(String isbn) {
        return bookRepository.findByISBN(isbn).get(0);
    }
    public Book findBookByName(String name) {
        return bookRepository.findByName(name).get(0);
    }

    public List<Book> findBookByAuthor(String author) {
        return bookRepository.findAllByAuthor(author);
    }
    public List<Book> findBookByCategory(String category) {
        return bookRepository.findAllByCategory(category);
    }
    public List<Book> findBookByLanguage(String language) {
        return bookRepository.findAllByLanguage(language);
    }

    public void updateBook(Book book) {bookRepository.save(book);}

    public void deleteBookById(Long id) {
        bookRepository.deleteBookById(id);
    }

}
