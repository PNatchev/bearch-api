package io.bearch.webapi.book_service;

import io.bearch.webapi.book_service.dto.BookDto;
import io.bearch.webapi.book_service.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn){
        return ResponseEntity.ok(bookService.getBookByISBN(isbn));
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookDto>> getBooksByAuthorName(@RequestParam String authorName){
        return ResponseEntity.ok(bookService.getBooksByAuthorName(authorName));
    }

    @PostMapping()
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.saveBook(bookDto));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBookByIsbn(@PathVariable String isbn){
            bookService.deleteBookByISBN(isbn);
            return ResponseEntity.ok().build();
    }
}
