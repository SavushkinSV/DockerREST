package com.example.servlet;

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

@WebServlet(name = "SimpleServlet", value = "/learner/*")
public class LearnerServlet extends HttpServlet {
    private final LearnerService service = LearnerServiceImpl.getInstance();
    private final LearnerDtoMapper mapper = LearnerDtoMapperImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String respString = "";
        try {
            String[] reqString = req.getPathInfo().split("/");
            if (reqString.length == 2) {
                Long id = Long.parseLong(reqString[1]);
                Learner learner = service.getById(id);
                if (learner != null) {
                    LearnerResponseDto responseDto = mapper.map(learner);
                    // return our DTO
                    resp.setStatus(HttpServletResponse.SC_OK);
                    respString = objectMapper.writeValueAsString(responseDto);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    respString = "Learner not found";
                }
            } else if (reqString.length == 0) {
                List<Learner> learnerList = service.getAll();
                // return our DTO
                List<LearnerResponseDto> responseDtoList = mapper.map(learnerList);
                resp.setStatus(HttpServletResponse.SC_OK);
                respString = objectMapper.writeValueAsString(responseDtoList);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            respString = "Bad request.";
            throw new IOException(e);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";

        // Чтение тела запроса и преобразование JSON в DTO
        LearnerRequestDto learnerRequestDto = objectMapper.readValue(req.getReader(), LearnerRequestDto.class);
        Learner addLearner = service.add(mapper.map(learnerRequestDto));

        LearnerResponseDto learnerResponseDto = mapper.map(addLearner);
        respString = objectMapper.writeValueAsString(learnerResponseDto);

        // Установка заголовка и типа содержимого
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_CREATED);

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";
        try {
            String[] reqString = req.getPathInfo().split("/");
            if (reqString.length == 2) {
                Long id = Long.parseLong(reqString[1]);
                service.delete(id);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            respString = "Bad request.";
//            throw new IOException(e);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();

    }
}
