package io.bearch.webapi.book_service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.bearch.webapi.book_service.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @JsonProperty(required = true)
    private String title;

    @JsonProperty(required = true)
    private String author;

    @JsonProperty(required = true)
    private String publisher;


    @JsonProperty(value = "publication_date", required = true)
    private String publicationDate;

    @JsonProperty(required = true)
    private String genre;

    @JsonProperty(required = true)
    private String isbn;

    @JsonProperty(required = true)
    private Double price;

    @JsonProperty(required = true)
    private String description;

    public static BookDto fromBook(Book book){
        return new BookDto(book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPublicationDate().toString(), book.getGenre(), book.getIsbn(), book.getPrice(), book.getDescription());
    }
}
