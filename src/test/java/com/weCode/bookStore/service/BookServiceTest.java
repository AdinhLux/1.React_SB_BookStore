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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
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
        assertThat(1).isEqualTo(bookDtos.size());
        assertThat(bookDtos.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "Test Dto title")
                .hasFieldOrPropertyWithValue("description", "Test Dto description")
                .hasFieldOrPropertyWithValue("releaseYear", 2021);
    }

    @Test
    void shouldReturnBooksByBookTitleIgnoreCase() {
        List<Book> books = new ArrayList<>();
        Book book = getBook();
        BookDto bookDto = getBookDto();
        books.add(book);

        // Mocking BookRepository function
        when(bookRepository.findBooksByTitleIgnoreCase(anyString())).thenReturn(books);

        // Mocking mapper
        when(mapper.map(book, BookDto.class)).thenReturn(bookDto);

        List<BookDto> bookDtoList = bookService.getBooksByTitle("test title");

        assertThat(bookDtoList.size()).isEqualTo(1);
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
