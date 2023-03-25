package io.bearch.webapi.book_service.service;

import io.bearch.webapi.book_service.domain.Book;
import io.bearch.webapi.book_service.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    public Book getBookByISBN(String isbn) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(isbn);
        if(bookOptional.isEmpty()){
            throw new NoSuchElementException("Book not found with ISBN: " + isbn);
        }

        return bookOptional.get();
    }

    //todo GetBookByTitle can do this with Moe
}
