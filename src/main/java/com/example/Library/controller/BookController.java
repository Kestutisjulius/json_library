package com.example.Library.controller;

import com.example.Library.config.BookJsonWriter;
import com.example.Library.config.JsonDataWriter;
import com.example.Library.exseption.BookNotFoundException;
import com.example.Library.model.Book;
import com.example.Library.model.User;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.UserRepository;
import com.example.Library.service.BookService;
import com.example.Library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    private final BookJsonWriter bookJsonWriter;
    private final JsonDataWriter jsonDataWriter;

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @GetMapping("/isbn/{ISBN}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable String ISBN) throws BookNotFoundException {
        Book book = bookService.findBookByISBN(ISBN);
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<Book> getBookByName(@PathVariable String name) throws BookNotFoundException {
        Book book = bookService.findBookByName(name);
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getAllBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findBookByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getAllBooksByCategory(@PathVariable String category) {
        List<Book> books = bookService.findBookByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<Book>> getAllBooksByLanguage(@PathVariable String language) {
        List<Book> books = bookService.findBookByLanguage(language);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Void> addBook(@RequestBody Book book) throws Exception {
        bookService.addBook(book);
        writeToJson();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/")
    public ResponseEntity<Void> updateBook(@RequestBody Book book, @PathVariable Long id) throws Exception {
        
        List<User> users = new ArrayList<User>();
        users = book.getUsers();
        users.add(userService.getUser(id));
        book.setUsers(users);
        
        bookService.updateBook(book);
    //    userService.updateUser(user1);
        writeToJson();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws Exception {
        bookService.deleteBookById(id);
        writeToJson();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void writeToJson() throws Exception {
        List<Book> books = bookService.findAllBooks();
        bookJsonWriter.writeJson(books);
        List<User> users = userService.findAllUsers();
        jsonDataWriter.writeJson(users);
    }
}
