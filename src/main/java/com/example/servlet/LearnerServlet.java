package com.example.servlet;

import com.example.model.Learner;
import com.example.services.LearnerService;
import com.example.services.impl.LearnerServiceImpl;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;
import com.example.servlet.mapper.LearnerDtoMapper;
import com.example.servlet.mapper.impl.LearnerDtoMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SimpleServlet", value = "/learner/*")
public class LearnerServlet extends HttpServlet {
    private final Gson gson = new Gson();
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
//                    respString = gson.toJson(responseDto, LearnerResponseDto.class);
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
                respString = responseDtoList.toString();
//                respString = gson.toJson(responseDtoList, LearnerResponseDto.class);
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";

        // Чтение тела запроса и преобразование JSON в DTO
        BufferedReader reader = req.getReader();
        LearnerRequestDto learnerRequestDto = objectMapper.readValue(reader, LearnerRequestDto.class);

//        Learner addLearner = service.add(mapper.map(learnerRequestDto));
        respString = learnerRequestDto.toString();

        // Установка заголовка и типа содержимого
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }
}
