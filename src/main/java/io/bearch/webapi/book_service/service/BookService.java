package io.bearch.webapi.book_service.service;

import io.bearch.webapi.book_service.dto.BookDto;

public interface BookService {
    BookDto getBookByISBN(String isbn);
    BookDto saveBook(BookDto bookDto);
}
