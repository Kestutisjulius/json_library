package com.example.Library.config;

import com.example.Library.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.util.List;

@Configuration
public class BookJsonWriter {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String file = "src/main/resources/json/books.json";

    public static String convertToJson(List<Book> books) throws JsonProcessingException {
        return mapper.writeValueAsString(books);
    }
    public void writeJson(List<Book> books) throws Exception{
        String json = convertToJson(books);
        try {
            FileWriter file = new FileWriter(BookJsonWriter.file);
            file.write(json);
            file.flush();
            file.close();
        } catch (Exception e) {e.printStackTrace();}
    }
}
