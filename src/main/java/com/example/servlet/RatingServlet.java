package com.example.servlet;

import com.example.exception.ObjectNotFoundException;
import com.example.model.Rating;
import com.example.services.RatingService;
import com.example.services.impl.RatingServiceImpl;
import com.example.servlet.dto.RatingRequestDto;
import com.example.servlet.dto.RatingResponseDto;
import com.example.servlet.mapper.RatingDtoMapper;
import com.example.servlet.mapper.impl.RatingDtoMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "RatingServlet", value = "/rating/*")
public class RatingServlet extends HttpServlet {
    private final transient RatingService service;
    private final transient RatingDtoMapper mapper;
    private final ObjectMapper objectMapper;

    private static final String NOT_FOUND_REQUEST_MESSAGE = "Rating not found.";
    private static final String BAD_REQUEST_MESSAGE = "Bad request.";

    public RatingServlet() {
        service = RatingServiceImpl.getInstance();
        mapper = RatingDtoMapperImpl.getInstance();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";
        int statusCode = HttpServletResponse.SC_BAD_REQUEST;
        try {
            String[] reqString = req.getPathInfo().split("/");
            if (reqString.length == 2) {
                Long id = Long.parseLong(reqString[1]);
                Rating rating = service.getById(id);
                RatingResponseDto responseDto = mapper.map(rating);
                statusCode = HttpServletResponse.SC_OK;
                respString = objectMapper.writeValueAsString(responseDto);

            } else if (reqString.length == 0) {
                List<Rating> ratingList = service.getAll();
                // return our DTO
                List<RatingResponseDto> responseDtos = mapper.map(ratingList);
                respString = objectMapper.writeValueAsString(responseDtos);
                statusCode = HttpServletResponse.SC_OK;
            }
        } catch (ObjectNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            respString = NOT_FOUND_REQUEST_MESSAGE;
        } catch (Exception e) {
            respString = BAD_REQUEST_MESSAGE;
        }
        setJsonHeader(resp, statusCode);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";

        // Чтение тела запроса и преобразование JSON в DTO
        RatingRequestDto ratingRequestDto = objectMapper.readValue(req.getReader(), RatingRequestDto.class);
        Rating addRating = service.add(mapper.map(ratingRequestDto));

        RatingResponseDto ratingResponseDto = mapper.map(addRating);
        respString = objectMapper.writeValueAsString(ratingResponseDto);

        setJsonHeader(resp, HttpServletResponse.SC_CREATED);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";
        int statusCode = HttpServletResponse.SC_BAD_REQUEST;
        try {
            String[] reqString = req.getPathInfo().split("/");
            if (reqString.length == 2) {
                Long id = Long.parseLong(reqString[1]);
                boolean status = service.delete(id);
                if (status) {
                    statusCode = HttpServletResponse.SC_OK;
                } else {
                    statusCode = HttpServletResponse.SC_NOT_FOUND;
                    respString = NOT_FOUND_REQUEST_MESSAGE;
                }
            }
        } catch (Exception e) {
            respString = BAD_REQUEST_MESSAGE;
        }
        setJsonHeader(resp, statusCode);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";
        int statusCode = HttpServletResponse.SC_NOT_FOUND;

        RatingRequestDto ratingRequestDto = objectMapper.readValue(req.getReader(), RatingRequestDto.class);
        try {
            service.getById(ratingRequestDto.getId());
            service.update(mapper.map(ratingRequestDto));
            statusCode = HttpServletResponse.SC_OK;
        } catch (ObjectNotFoundException e) {
            respString = NOT_FOUND_REQUEST_MESSAGE;
        }
        setJsonHeader(resp, statusCode);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }

    /**
     * Устанавливает заголовок и тип содержимого.
     */
    private static void setJsonHeader(HttpServletResponse resp, int statusCode) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(statusCode);
    }
}
