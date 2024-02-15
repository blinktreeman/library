package ru.letsdigit.library.uiController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.service.BookService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookUIController.class)
public class BookUIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new BookUIController(bookService))
                .build();
    }

    @Test
    public void allBooksPageTest() throws Exception {
        Book b1 = new Book("Pro Spring Security");
        Book b2 = new Book("Java Persistence with Hibernate");

        Mockito.when(bookService.findAll()).thenReturn(Arrays.asList(b1, b2));

        mockMvc.perform(get("/ui/book/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("books"));;
    }
}
