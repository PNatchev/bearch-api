package io.bearch.webapi.book_service;

import io.bearch.webapi.book_service.domain.Book;
import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.repository.BookRepository;
import io.bearch.webapi.book_service.service.BookService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTests extends BookBaseTest {

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
        BookDto actualBook = bookService.getBookByISBN("978-0-385-48451-0");

        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.getPublicationDate().toString(), actualBook.getPublicationDate());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook.getPrice(), actualBook.getPrice());
    }
}
