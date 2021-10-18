package com.weCode.bookStore.controller;

import com.weCode.bookStore.dto.BookDto;
import com.weCode.bookStore.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//
//

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    // Inject the controller
    @InjectMocks
    private BookController bookController;

    // Inject Book service and annotate with Mock to get the fake instance
    @Mock
    private BookService bookService;

    @Test
    void shouldReturnBookDtoListWhenGetBooksCalled() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(getBookDto());

        when(bookService.getBooks()).thenReturn(bookDtos);
        ResponseEntity<List<BookDto>> books = bookController.getBooks();
        assertThat(books.getBody()).isNotNull();
        assertThat(books.getBody().size()).isEqualTo(1);
    }

    @Test
    void shouldReturnBookDtoListWhenGetBooksByTitleCalled() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(getBookDto());

        when(bookService.getBooksByTitle(anyString())).thenReturn(bookDtos);
        ResponseEntity<List<BookDto>> books = bookController.getBooksByTitle("test title");
        assertThat(books.getBody()).isNotNull();
        assertThat(books.getBody().size()).isEqualTo(1);
    }

    private BookDto getBookDto() {
        return BookDto.builder()
                .title("Test title")
                .description("Test description")
                .id(UUID.randomUUID())
                .releaseYear(2020)
                .build();
    }
}
