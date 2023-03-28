package io.bearch.webapi.book_service;

import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<BookDto> getBookByIsbn(@RequestParam String isbn){
         return ResponseEntity.ok(bookService.getBookByISBN(isbn));
    }
}
