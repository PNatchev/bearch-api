package io.bearch.webapi.book_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book extends AbstractPersistable<UUID> {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'N/A'")
    private String isbn; //ISBN did not exist prior to 1970

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    private Double price;

    @Column(nullable = false)
    private String description;

}
