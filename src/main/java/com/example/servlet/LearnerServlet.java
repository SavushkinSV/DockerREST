package com.example.servlet;

import com.example.exception.ObjectNotFoundException;
import com.example.model.Learner;
import com.example.services.LearnerService;
import com.example.services.impl.LearnerServiceImpl;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;
import com.example.servlet.mapper.LearnerDtoMapper;
import com.example.servlet.mapper.impl.LearnerDtoMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "LearnerServlet", value = "/learner/*")
public class LearnerServlet extends HttpServlet {
    private final transient LearnerService service;
    private final transient LearnerDtoMapper mapper;
    private final ObjectMapper objectMapper;

    private static final String NOT_FOUND_REQUEST_MESSAGE = "Learner not found.";
    private static final String BAD_REQUEST_MESSAGE = "Bad request.";

    public LearnerServlet() {
        service = LearnerServiceImpl.getInstance();
        mapper = LearnerDtoMapperImpl.getInstance();
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
                Learner learner = service.getById(id);
                LearnerResponseDto responseDto = mapper.map(learner);
                // return our DTO
                statusCode = HttpServletResponse.SC_OK;
                respString = objectMapper.writeValueAsString(responseDto);
            } else if (reqString.length == 0) {
                List<Learner> learnerList = service.getAll();
                // return our DTO
                List<LearnerResponseDto> responseDtoList = mapper.map(learnerList);
                statusCode = HttpServletResponse.SC_OK;
                respString = objectMapper.writeValueAsString(responseDtoList);
            }
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            respString = NOT_FOUND_REQUEST_MESSAGE;
        } catch (Exception e) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
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
        int statusCode = HttpServletResponse.SC_OK;

        // Чтение тела запроса и преобразование JSON в DTO
        LearnerRequestDto learnerRequestDto = objectMapper.readValue(req.getReader(), LearnerRequestDto.class);
        Learner addLearner = service.add(mapper.map(learnerRequestDto));

        LearnerResponseDto learnerResponseDto = mapper.map(addLearner);
        respString = objectMapper.writeValueAsString(learnerResponseDto);

        setJsonHeader(resp, statusCode);
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
                    respString = NOT_FOUND_REQUEST_MESSAGE;
                    statusCode = HttpServletResponse.SC_NOT_FOUND;
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
        try {
            LearnerRequestDto learnerRequestDto = objectMapper.readValue(req.getReader(), LearnerRequestDto.class);

            service.getById(learnerRequestDto.getId());
            service.update(mapper.map(learnerRequestDto));
            statusCode = HttpServletResponse.SC_OK;
        } catch (ObjectNotFoundException e) {
            respString = NOT_FOUND_REQUEST_MESSAGE;
        } catch (IOException e) {
            respString = BAD_REQUEST_MESSAGE;
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
