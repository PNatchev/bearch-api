package io.bearch.webapi.book_service.service;

import io.bearch.webapi.book_service.domain.Book;
import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    public BookDto getBookByISBN(String isbn) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(isbn);
        if(bookOptional.isEmpty()){
            throw new NoSuchElementException("No book was found with the provided ISBN: " + isbn);
        }

        return BookDto.fromBook(bookOptional.get());
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(bookDto.getIsbn());
        if(bookOptional.isPresent()){
            throw new EntityExistsException("The following book already exists.");
        }
        else{
           return BookDto.fromBook(bookRepository.saveAndFlush(Book.fromBookDto(bookDto)));
        }
    }

    @Override
    public void deleteBookByISBN(String isbn) {
        Optional<Book> bookOptional =  bookRepository.findBookByIsbn(isbn);

        if(bookOptional.isPresent()){
           bookRepository.deleteById(bookOptional.get().getId());
        }
        else{
            log.error("No book with isbn: " + isbn + " exists.");
        }
    }
}
