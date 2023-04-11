package io.bearch.webapi.book_service.service;

import io.bearch.webapi.book_service.model.Book;
import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<BookDto> getBooksByAuthorName(String authorName) {
        List<Book> bookList = bookRepository.findBooksByAuthor(authorName);
        if(bookList.isEmpty()){
            throw new NoSuchElementException("No books found by author name: " + authorName);
        }
        List<BookDto> returnBookList = new ArrayList<>();
        bookList.stream().map(book -> returnBookList.add(BookDto.fromBook(book))).collect(Collectors.toList());

        return returnBookList;
    }
}
