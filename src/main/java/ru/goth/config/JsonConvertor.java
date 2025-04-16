package ru.goth.config;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonConvertor<T> {
    public void convertToJson(HttpServletResponse response, T dto) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(dto);

        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        printWriter.write(json);
        printWriter.close();
    }
}
