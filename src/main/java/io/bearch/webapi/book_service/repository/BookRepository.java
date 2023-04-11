package io.bearch.webapi.book_service.repository;

import io.bearch.webapi.book_service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    Optional<Book> findBookByIsbn(String isbn);

    @Query("SELECT e from book e WHERE e.author LIKE %:authorName%")
    List<Book> findBooksByAuthor(@Param("authorName") String authorName);

}
