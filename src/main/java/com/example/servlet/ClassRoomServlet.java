package com.example.servlet;

import com.example.exception.ObjectNotFoundException;
import com.example.model.ClassRoom;
import com.example.services.ClassRoomService;
import com.example.services.impl.ClassRoomServiceImpl;
import com.example.servlet.dto.ClassRoomResponseDto;
import com.example.servlet.mapper.ClassRoomDtoMapper;
import com.example.servlet.mapper.impl.ClassRoomDtoMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ClassRoomServlet", value = "/class_room/*")
public class ClassRoomServlet extends HttpServlet {
    private final transient ClassRoomService service;
    private final transient ClassRoomDtoMapper mapper;
    private final ObjectMapper objectMapper;

    public ClassRoomServlet() {
        service = ClassRoomServiceImpl.getInstance();
        mapper = ClassRoomDtoMapperImpl.getInstance();
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

                ClassRoom classRoom = service.getById(id);
                ClassRoomResponseDto responseDto = mapper.map(classRoom);
                // return our DTO
                statusCode = HttpServletResponse.SC_OK;
                respString = objectMapper.writeValueAsString(responseDto);

            } else if (reqString.length == 0) {
                List<ClassRoom> classRoomList = service.getAll();
                // return our DTO
                List<ClassRoomResponseDto> responseDtoList = mapper.map(classRoomList);
                statusCode = HttpServletResponse.SC_OK;
                respString = objectMapper.writeValueAsString(responseDtoList);
            }
        } catch (ObjectNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            respString = "ClassRoom not found";
        } catch (Exception e) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
            respString = "Bad request.";
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
