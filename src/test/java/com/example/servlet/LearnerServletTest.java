package com.example.servlet;

import com.example.model.Learner;
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

public class LearnerServletTest {
    private static LearnerServlet servlet;
    private static LearnerService service = LearnerServiceImpl.getInstance();
    private static LearnerServiceImpl oldInstance;

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
            oldInstance = (LearnerServiceImpl) instance.get(instance);
            instance.set(instance, service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        servlet = new LearnerServlet();
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
            instance.set(instance, oldInstance);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void doGetFindByIdTest() throws IOException {
        Learner learner = new Learner(1L, "firstName", "lastName", null, null);
        Mockito.doReturn("learner/1").when(mockReq).getPathInfo();
        Mockito.doReturn(learner).when(service).getById(Mockito.anyLong());

        servlet.doGet(mockReq, mockResp);

        Mockito.verify(service).getById(1L);
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doGetFindByIdWithNullTest() throws IOException {
        Mockito.doReturn("learner/2").when(mockReq).getPathInfo();
        Mockito.doReturn(null).when(service).getById(Mockito.anyLong());

        servlet.doGet(mockReq, mockResp);

        Mockito.verify(service).getById(2L);
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void doGetAllTest() throws IOException {
        Mockito.doReturn("/").when(mockReq).getPathInfo();

        servlet.doGet(mockReq, mockResp);

        Mockito.verify(service).getAll();
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doPostTest() throws IOException {
        String firstNam = "firstName";
        String lastName = "lastName";
        LearnerRequestDto dto = new LearnerRequestDto(null, firstNam, lastName, null);
        Learner learner = new Learner(1L, firstNam, lastName, null, null);
        Gson gson = new Gson();
        BufferedReader mockReader = new BufferedReader(new StringReader(gson.toJson(dto)));
        Mockito.doReturn(mockReader).when(mockReq).getReader();
        Mockito.doReturn(learner).when(service).add(Mockito.any(Learner.class));

        servlet.doPost(mockReq, mockResp);

        Mockito.verify(service).add(Mockito.any(Learner.class));
    }


    @Test
    public void doDeleteTest() throws IOException {
        Mockito.doReturn("learner/1").when(mockReq).getPathInfo();
        Mockito.doReturn(true).when(service).delete(Mockito.anyLong());

        servlet.doDelete(mockReq, mockResp);

        Mockito.verify(service).delete(1L);
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void doDeleteWithBadIdTest() throws IOException {
        Mockito.doReturn("learner/1").when(mockReq).getPathInfo();
        Mockito.doReturn(false).when(service).delete(Mockito.anyLong());

        servlet.doDelete(mockReq, mockResp);

        Mockito.verify(service).delete(Mockito.anyLong());
        Mockito.verify(mockResp).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

}
