package io.bearch.webapi.utility;

import io.bearch.webapi.book_service.domain.Book;
import io.bearch.webapi.utility.csv_processor.CsvReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvReaderTests {

    private CsvReader csvReader = new CsvReader();

    @Test
    void shouldReadCsv() throws IOException {
        Integer expectedSize = 12;
        List<Book> books = csvReader.readCsv("book-data.csv", Book.class);

        assertEquals(expectedSize, books.size());
        assertEquals("The Hunger Games",books.get(0).getTitle());
        assertEquals("Suzanne Collins",books.get(0).getAuthor());
        assertEquals("Scholastic Press",books.get(0).getPublisher());
        assertEquals(LocalDate.of(2008,9,14), books.get(0).getPublicationDate());
        assertEquals("Science Fiction", books.get(0).getGenre());
        assertEquals("9780439023481", books.get(0).getIsbn());
        assertEquals(8.99, books.get(0).getPrice());
        assertTrue(books.get(0).getDescription().contains("In a dystopian future"));
    }

    @Test
    void shouldThrowNullPointerException_WhenUsingWrongFileName(){
        assertThrows(NullPointerException.class, () ->{
            csvReader.readCsv("incorrectName.csv", Book.class);
        });
    }
}
