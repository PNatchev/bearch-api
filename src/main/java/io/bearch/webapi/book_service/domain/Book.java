package io.bearch.webapi.book_service.domain;

import com.opencsv.bean.CsvDate;
import io.bearch.webapi.book_service.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    //This setup avoids repeating 0's in H2 DB
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @CsvDate("yyyy-MM-dd")
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'N/A'")
    private String isbn;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    private Double price;

    @Column(nullable = false)
    private String description;

    public static Book fromBookDto(BookDto bookDto){
        return new Book(null, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPublisher(), LocalDate.parse(bookDto.getPublicationDate()), bookDto.getGenre(), bookDto.getIsbn(), bookDto.getPrice(), bookDto.getDescription());
    }

}
