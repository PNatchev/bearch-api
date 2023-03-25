package io.bearch.webapi.book_service.service;

import io.bearch.webapi.book_service.domain.Book;

public interface BookService {
    Book getBookByISBN(String isbn);
}
