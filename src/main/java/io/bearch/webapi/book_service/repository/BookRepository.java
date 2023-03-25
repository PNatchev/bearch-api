package io.bearch.webapi.book_service.repository;

import io.bearch.webapi.book_service.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    //Custom query creation
}