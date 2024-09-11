package com.example.servlet;

import com.example.model.Learner;
import com.example.services.LearnerService;
import com.example.services.impl.LearnerServiceImpl;
import com.example.servlet.dto.LearnerResponseDto;
import com.example.servlet.mapper.LearnerDtoMapper;
import com.example.servlet.mapper.LearnerDtoMapperImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SimpleServlet", value = "/learner")
public class LearnerServlet extends HttpServlet {
    private final LearnerService service = LearnerServiceImpl.getInstance();
    private LearnerDtoMapper mapper = LearnerDtoMapperImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = 1L;// Our Id from request
        Learner learner = service.getById(id);
        LearnerResponseDto responseDTO = mapper.map(learner);
        // return our DTO
    }
}
