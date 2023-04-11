package io.bearch.webapi.book_service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bearch.webapi.book_service.model.Book;
import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.repository.BookRepository;
import io.bearch.webapi.config.security.SecurityConfig;
import io.bearch.webapi.token_service.AuthController;
import io.bearch.webapi.token_service.TokenService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceControllerTests extends BookBaseTest{

    @MockBean
    private AuthController authController;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private WebSecurityConfiguration webSecurityConfiguration;

    @MockBean
    private SecurityConfig securityConfig;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    private Book book;


    @BeforeAll
    void setup() {
        book = Book.builder()
                .title("Tuesdays with Morrie")
                .author("Mitch Albom")
                .publisher("Penguin Random House")
                .publicationDate(LocalDate.of(1997, 8, 18))
                .genre("Self-Help")
                .isbn("978-0-385-48451-0")
                .price(14.95)
                .description("Example description of Mitch Albom")
                .build();
        book = bookRepository.save(book);
    }

    @AfterAll
    void tearDown(){
        bookRepository.delete(book);
    }

    @Test
    void shouldGetBookByISBN_Controller() throws Exception {
       Book expectedBook = book;

       MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/api/book/978-0-385-48451-0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

       String responseBody = result.getResponse().getContentAsString();
       ObjectMapper objectMapper = new ObjectMapper();
       BookDto actualBook = objectMapper.readValue(responseBody, BookDto.class);

        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.getPublicationDate().toString(), actualBook.getPublicationDate());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook.getPrice(), actualBook.getPrice());
    }

    @Test
    void shouldSaveBook_Controller() throws Exception {
        ObjectMapper objectToJson = new ObjectMapper();

        BookDto expectedBook = BookDto.builder()
                .title("TestBookTitle")
                .author("TestAuthorTitle")
                .publisher("TestPublisher")
                .publicationDate(LocalDate.now().toString())
                .genre("TestGenre")
                .isbn("TestIsbn")
                .price(16.69)
                .description("TestDescription")
                .build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson.writeValueAsString(expectedBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper jsonToObject = new ObjectMapper();
        BookDto actualBook = jsonToObject.readValue(responseBody, BookDto.class);

        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.getPublicationDate(), actualBook.getPublicationDate());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook.getPrice(), actualBook.getPrice());
    }

    @Transactional
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/book/978-0-385-48451-0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(bookRepository.findBookByIsbn(book.getIsbn()).isPresent());
    }

    @Transactional
    @Test
    void shouldGetBooksByAuthorName() throws Exception {
        Book expectedBook = book;

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/book/author")
                        .param("authorName", "Mitch")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<BookDto> actualBook = objectMapper.readValue(responseBody, new TypeReference<>() {});

        assertEquals(expectedBook.getTitle(), actualBook.get(0).getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.get(0).getAuthor());
        assertEquals(expectedBook.getIsbn(), actualBook.get(0).getIsbn());
        assertEquals(expectedBook.getGenre(), actualBook.get(0).getGenre());
        assertEquals(expectedBook.getDescription(), actualBook.get(0).getDescription());
        assertEquals(expectedBook.getPublicationDate().toString(), actualBook.get(0).getPublicationDate());
        assertEquals(expectedBook.getPublisher(), actualBook.get(0).getPublisher());
        assertEquals(expectedBook.getPrice(), actualBook.get(0).getPrice());
    }
}
