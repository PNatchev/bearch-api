package io.bearch.webapi.book_service;

import io.bearch.webapi.book_service.model.Book;
import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.repository.BookRepository;
import io.bearch.webapi.book_service.service.BookService;
import io.bearch.webapi.config.security.SecurityConfig;
import io.bearch.webapi.token_service.AuthController;
import io.bearch.webapi.token_service.TokenService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTests extends BookBaseTest {

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
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeAll
    void setup(){
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

    @Transactional
    @Test
    void shouldGetBookByISBN(){
        Book expectedBook = book;
        BookDto actualBook = bookService.getBookByISBN(expectedBook.getIsbn());

        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.getPublicationDate().toString(), actualBook.getPublicationDate());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook.getPrice(), actualBook.getPrice());
    }

    @Transactional
    @Test
    void shouldSaveBook(){
        Book expectedBook = Book.builder()
                .title("Friday with Jason")
                .author("Tom Momoa")
                .publisher("Cow Intended House")
                .publicationDate(LocalDate.of(2000, 9, 20))
                .genre("Thriller")
                .isbn("9999-0-385-48423-0")
                .price(16.75)
                .description("Test description")
                .build();

        BookDto actualBook = bookService.saveBook(BookDto.fromBook(expectedBook));

        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook.getPublicationDate().toString(), actualBook.getPublicationDate());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getPrice(), actualBook.getPrice());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
    }

    @Transactional
    @Test
    void shouldThrowError_WhenSavingExistingBook(){
        assertThrows(EntityExistsException.class, () ->{
            bookService.saveBook(BookDto.fromBook(book));
        });
    }

    @Transactional
    @Test
    void shouldDeleteBook(){
        Book expectedBook = Book.builder()
                .title("Friday with Jason")
                .author("Tom Momoa")
                .publisher("Cow Intended House")
                .publicationDate(LocalDate.of(2000, 9, 20))
                .genre("Thriller")
                .isbn("9999-0-385-48423-0")
                .price(16.75)
                .description("Test description")
                .build();

        Book actualBook = bookRepository.save(expectedBook);
        bookService.deleteBookByISBN(actualBook.getIsbn());
        Optional<Book> bookOptional = bookRepository.findById(actualBook.getId());

        assertFalse(bookOptional.isPresent());
    }
}
