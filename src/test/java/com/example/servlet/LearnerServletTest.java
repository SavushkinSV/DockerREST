package com.example.servlet;

import com.example.exception.ObjectNotFoundException;
import com.example.model.ClassRoom;
import com.example.model.Learner;
import com.example.model.Rating;
import com.example.services.LearnerService;
import com.example.services.impl.LearnerServiceImpl;
import com.example.servlet.dto.LearnerRequestDto;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class LearnerServletTest {
    private static LearnerRequestDto dto;
    private static Learner learner;
    private static LearnerServlet servlet;
    private static LearnerService service = LearnerServiceImpl.getInstance();
    private static Gson gson;

    @Mock
    private HttpServletRequest mockReq;

    @Mock
    private HttpServletResponse mockResp;

    @BeforeAll
    static void beforeAll() {
        service = Mockito.mock(LearnerServiceImpl.class);
        try {
            Field instance = LearnerServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        servlet = new LearnerServlet();

        dto = new LearnerRequestDto(1L, "firstName", "lastName", new ClassRoom(1L, "1а", null));
        List<Learner> learnerList = new ArrayList<>();
        learnerList.add(new Learner(1L, "first", "last", null, null));
        learner = new Learner(1L, "firstName", "lastName", new ClassRoom(1L, "1а", learnerList), null);
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(new Rating(1L, "2021-01-01", 5, "Test"));
        learner.setRatingList(ratingList);
        gson = new Gson();
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResp).getWriter();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(service);
    }

    @AfterAll
    static void afterAll() {
        try {
            Field instance = LearnerServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void doGetByIdTest() throws IOException, ObjectNotFoundException {
        Mockito.doReturn("learner/1").when(mockReq).getPathInfo();
        Mockito.doReturn(learner).when(service).getById(Mockito.anyLong());

        servlet.doGet(mockReq, mockResp);

        Mockito.verify(service).getById(Mockito.anyLong());
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doGetByIdThrowsExceptionTest() throws IOException, ObjectNotFoundException {
        Mockito.doReturn("learner/2").when(mockReq).getPathInfo();
        Mockito.when(service.getById(Mockito.anyLong())).thenThrow(ObjectNotFoundException.class);

        servlet.doGet(mockReq, mockResp);

        Mockito.verify(service).getById(2L);
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetAllTest() throws IOException {
        Mockito.doReturn("/").when(mockReq).getPathInfo();

        servlet.doGet(mockReq, mockResp);

        Mockito.verify(service).getAll();
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPostTest() throws IOException {
        BufferedReader mockReader = new BufferedReader(new StringReader(gson.toJson(dto)));
        Mockito.doReturn(mockReader).when(mockReq).getReader();
        Mockito.doReturn(learner).when(service).add(Mockito.any(Learner.class));

        servlet.doPost(mockReq, mockResp);

        Mockito.verify(service).add(Mockito.any(Learner.class));
    }


    @Test
    void doDeleteTest() throws IOException {
        Mockito.doReturn("learner/1").when(mockReq).getPathInfo();
        Mockito.doReturn(true).when(service).delete(Mockito.anyLong());

        servlet.doDelete(mockReq, mockResp);

        Mockito.verify(service).delete(1L);
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDeleteWithBadIdTest() throws IOException {
        Mockito.doReturn("learner/1").when(mockReq).getPathInfo();
        Mockito.doReturn(false).when(service).delete(Mockito.anyLong());

        servlet.doDelete(mockReq, mockResp);

        Mockito.verify(service).delete(Mockito.anyLong());
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doPutTest() throws IOException, ObjectNotFoundException {
        BufferedReader mockReader = new BufferedReader(new StringReader(gson.toJson(dto)));
        Mockito.doReturn(mockReader).when(mockReq).getReader();
        Mockito.doReturn(learner).when(service).getById(Mockito.anyLong());

        servlet.doPut(mockReq, mockResp);

        Mockito.verify(service).update(Mockito.any(Learner.class));
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPutWithNullTest() throws IOException, ObjectNotFoundException {
        BufferedReader mockReader = new BufferedReader(new StringReader(gson.toJson(dto)));
        Mockito.doReturn(mockReader).when(mockReq).getReader();
        Mockito.when(service.getById(Mockito.anyLong())).thenThrow(ObjectNotFoundException.class);

        servlet.doPut(mockReq, mockResp);

        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
