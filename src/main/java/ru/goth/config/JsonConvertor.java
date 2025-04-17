package ru.goth.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonConvertor<T> {
    private final Gson gson;

    public JsonConvertor() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void convertToJson(HttpServletResponse response, T dto) throws IOException {
        String json = gson.toJson(dto);

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
        }
    }
}
