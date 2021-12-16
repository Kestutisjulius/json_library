package com.example.Library.config;

import com.example.Library.model.Book;
import com.example.Library.model.User;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Configuration
public class JsonDataFiller {

    public static final List<User>userList = getAllUsersFromJsonFile();
    public static final List<Book>booksList = getAllBooksFromJsonFile();
    public static final String JSON_PATH = "src/main/resources/json/users.json";
    public static final String BOOKS_JSON_PATH = "src/main/resources/json/books.json";

    public static List<User> getAllUsersFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = readFileAsString(JSON_PATH);
            User[] users = mapper.readValue(json, User[].class);
            return new ArrayList<>(Arrays.asList(users));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Book> getAllBooksFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = readFileAsString(BOOKS_JSON_PATH);
            Book[] books = mapper.readValue(json, Book[].class);
            return new ArrayList<>(Arrays.asList(books));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Bean
    CommandLineRunner commandLineRunner (UserRepository userRepository, BookRepository bookRepository) {
        return args -> {
            userRepository.saveAll(userList);
            bookRepository.saveAll(booksList);
        };
    }
}
