package io.bearch.webapi.book_service.service;

import io.bearch.webapi.book_service.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto getBookByISBN(String isbn);
    BookDto saveBook(BookDto bookDto);
    void deleteBookByISBN(String isbn);
    List<BookDto> getBooksByAuthorName(String authorName);
}
