package com.example.Library.controller;

import com.example.Library.config.BookJsonWriter;
import com.example.Library.config.JsonDataWriter;
import com.example.Library.model.Book;
import com.example.Library.model.User;
import com.example.Library.exseption.*;
import com.example.Library.service.BookService;
import com.example.Library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class AdminController {

    private final JsonDataWriter jsonDataWriter;
    private final BookJsonWriter bookJsonWriter;

    private final UserService userService;

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }
    @PostMapping()
    public ResponseEntity<Void> addUser(@RequestBody User user) throws Exception {
        userService.addUser(user);
        writeToJson();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody User user) throws Exception {
        userService.updateUser(user);
        writeToJson();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        writeToJson();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void writeToJson() throws Exception {
        List<User> users = userService.findAllUsers();
        jsonDataWriter.writeJson(users);
        List<Book> books = bookService.findAllBooks();
        bookJsonWriter.writeJson(books);
    }

}
