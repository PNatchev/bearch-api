package io.bearch.webapi.config.data_loader;

import io.bearch.webapi.book_service.domain.Book;
import io.bearch.webapi.book_service.repository.BookRepository;
import io.bearch.webapi.utility.csv_processor.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BookLoader implements ApplicationRunner {

    private CsvReader csvReader ;
    private BookRepository bookRepository;

    @Autowired
    public BookLoader(CsvReader csvReader, BookRepository bookRepository){
        this.csvReader = csvReader;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Book> books = csvReader.readCsv("book-data.csv", Book.class);
        bookRepository.saveAllAndFlush(books);
        log.info("Book data successfully loaded.");
        log.info("http://localhost:8080/h2-console");
    }
}
