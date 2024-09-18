package com.example.servlet;

import com.example.model.Rating;
import com.example.services.RatingService;
import com.example.services.impl.RatingServiceImpl;
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
    private static final RatingService service = RatingServiceImpl.getInstance();
    private static final RatingDtoMapper mapper = RatingDtoMapperImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String respString = "";
        try {
            String[] reqString = req.getPathInfo().split("/");
            if (reqString.length == 2) {
                Long id = Long.parseLong(reqString[1]);
                Rating rating = service.getById(id);
                if (rating != null) {
                    RatingResponseDto responseDto = mapper.map(rating);
                    // return our DTO
                    resp.setStatus(HttpServletResponse.SC_OK);
                    respString = objectMapper.writeValueAsString(responseDto);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    respString = "Rating not found";
                }
            } else if (reqString.length == 0) {
                List<Rating> ratingList = service.getAll();
                // return our DTO
                List<RatingResponseDto> responseDtos = mapper.map(ratingList);
                resp.setStatus(HttpServletResponse.SC_OK);
                respString = objectMapper.writeValueAsString(responseDtos);
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
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(respString);
        printWriter.flush();
    }
}
