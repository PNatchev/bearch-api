package io.bearch.webapi.book_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bearch.webapi.book_service.domain.Book;
import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.repository.BookRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceControllerTests extends BookBaseTest{

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
                .get("/api/book")
                .param("isbn", "978-0-385-48451-0")
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
}
