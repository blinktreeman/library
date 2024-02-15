package ru.letsdigit.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.letsdigit.library.entity.Reader;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderControllerTest {

    private final Reader TEST_READER = new Reader("Ivan", "Ivanov");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private static String readerUuid;

    @Test
    public void saveTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        post("/api/v1/reader")
                                .content(objectMapper.writeValueAsString(this.TEST_READER))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName")
                        .value(this.TEST_READER.getFirstName()))
                .andExpect(jsonPath("$.lastName")
                        .value(this.TEST_READER.getLastName()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        readerUuid = String.valueOf(objectMapper.readValue(response, Reader.class).getUuid());
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(
                        get("/api/v1/reader/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(
                        get("/api/v1/reader/{id}", readerUuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName")
                        .value(this.TEST_READER.getFirstName()))
                .andExpect(jsonPath("$.lastName")
                        .value(this.TEST_READER.getLastName()));
    }

    @Test
    public void getWithWrongUuid_withStatus404() throws Exception {
        mockMvc.perform(
                        get("/api/v1/reader/{id}", String.valueOf(UUID.randomUUID())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        post("/api/v1/reader")
                                .content(objectMapper.writeValueAsString(this.TEST_READER))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName")
                        .value(this.TEST_READER.getFirstName())).andReturn();
        String response = result.getResponse().getContentAsString();
        String resultUuid = String.valueOf(objectMapper.readValue(response, Reader.class).getUuid());

        mockMvc.perform(delete("/api/v1/reader/{id}", resultUuid))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/reader/{id}", resultUuid))
                .andExpect(status().isNotFound());
    }
}
