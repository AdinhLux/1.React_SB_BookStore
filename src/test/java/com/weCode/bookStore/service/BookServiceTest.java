package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.BookDto;
import com.weCode.bookStore.model.Book;
import com.weCode.bookStore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

//
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    /*
     * Let's inject BookService. Only him is the actual instance.
     * The others are proxies for the actual instance
     */
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void shouldReturnListOfBookDtoWhenGetBooksCalled() {
        List<Book> books = new ArrayList<>();
        Book book = getBook();
        BookDto bookDto = getBookDto();

        books.add(book);
        when(bookRepository.findAll()).thenReturn(books);
        when(mapper.map(book, BookDto.class)).thenReturn(bookDto);

        // Call the Book service
        List<BookDto> bookDtos = bookService.getBooks();

    }

    private Book getBook() {
        return Book.builder()
                .title("Test title")
                .description("Test description")
                .id(UUID.randomUUID())
                .releaseYear(2020)
                .build();
    }

    private BookDto getBookDto() {
        return BookDto.builder()
                .title("Test Dto title")
                .description("Test Dto description")
                .id(UUID.randomUUID())
                .releaseYear(2021)
                .build();
    }
}
