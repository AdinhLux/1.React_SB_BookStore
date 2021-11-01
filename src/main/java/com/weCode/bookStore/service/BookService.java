package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.BookDto;
import com.weCode.bookStore.model.Book;
import com.weCode.bookStore.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/*
 * @Service is used to indicate that they're holding the business logic.
 * Beside being used in the service layer, there isn't any other special use for this annotation.
 *
 * https://www.baeldung.com/spring-component-repository-service
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    // We need to create a Mapper.java class in config package to remove 'Could not autowire. No beans of 'ModelMapper' type found.'
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDto> getBooks() {
        Iterable<Book> allBooks = bookRepository.findAll();
        return StreamSupport.stream(allBooks.spliterator(), false)
                .map(convertBookModelToBookDto())
                .collect(Collectors.toList());
    }

    private Function<Book, BookDto> convertBookModelToBookDto() {
        return book -> modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getBooksByTitle(String bookTitle) {
        List<Book> booksByTitle = bookRepository.findBooksByTitleIgnoreCase(bookTitle);

        return booksByTitle.stream()
                .map(convertBookModelToBookDto())
                .collect(Collectors.toList());
    }
}
