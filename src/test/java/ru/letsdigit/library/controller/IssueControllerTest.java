package ru.letsdigit.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.entity.Issue;
import ru.letsdigit.library.entity.Reader;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IssueControllerTest {

    private static Book TEST_BOOK = new Book("Simple book");
    private static Reader TEST_READER = new Reader("Ivan", "Ivanov");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    void setup() throws Exception {
        MvcResult bookResult = mockMvc.perform(
                        post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(TEST_BOOK)))
                .andReturn();
        TEST_BOOK = objectMapper.readValue(bookResult.getResponse().getContentAsString(), Book.class);

        MvcResult readerResult = mockMvc.perform(
                        post("/api/v1/reader")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(TEST_READER)))
                .andReturn();
        TEST_READER = objectMapper.readValue(readerResult.getResponse().getContentAsString(), Reader.class);
    }

    @Test
    public void saveTest() throws Exception {
        Issue issue = new Issue();
        issue.setBook(TEST_BOOK);
        issue.setReader(TEST_READER);

        mockMvc.perform(
                        post("/api/v1/issue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(issue)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.book.bookTitle").value(TEST_BOOK.getBookTitle()))
                .andExpect(jsonPath("$.reader.firstName").value(TEST_READER.getFirstName()))
                .andExpect(jsonPath("$.reader.lastName").value(TEST_READER.getLastName()));
    }

    @Test
    public void findByIdTest() throws Exception {
        Issue issue = new Issue();
        issue.setBook(TEST_BOOK);
        issue.setReader(TEST_READER);

        MvcResult result = mockMvc.perform(
                        post("/api/v1/issue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(issue)))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        String issueUuid = String.valueOf(objectMapper.readValue(response, Issue.class).getUuid());

        mockMvc.perform(
                        get("/api/v1/issue/{id}", issueUuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.book.bookTitle").value(TEST_BOOK.getBookTitle()));
    }

    @Test
    public void getWithWrongUuid_withStatus404() throws Exception {
        mockMvc.perform(
                        get("/api/v1/issue/{id}", String.valueOf(UUID.randomUUID())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTest() throws Exception {
        Issue issue = new Issue();
        issue.setBook(TEST_BOOK);
        issue.setReader(TEST_READER);

        MvcResult result = mockMvc.perform(
                        post("/api/v1/issue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(issue)))
                .andExpect(jsonPath("$.returnedAt").isEmpty())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        String issueUuid = String.valueOf(objectMapper.readValue(response, Issue.class).getUuid());

        mockMvc.perform(
                        put("/api/v1/issue/{id}/turned", issueUuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.returnedAt").isNotEmpty());
    }
}
