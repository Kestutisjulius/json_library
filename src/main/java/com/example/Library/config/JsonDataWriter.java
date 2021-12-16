package com.example.Library.config;

import com.example.Library.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.util.List;

@Configuration
public class JsonDataWriter {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String file = "src/main/resources/json/users.json";

    public static String convertToJson(List<User> users) throws JsonProcessingException {
        return mapper.writeValueAsString(users);
    }
    public void writeJson(List<User> users) throws Exception{
        String json = convertToJson(users);
        try {
            FileWriter file = new FileWriter(JsonDataWriter.file);
            file.write(json);
            file.flush();
            file.close();
        } catch (Exception e) {e.printStackTrace();}
    }
}
