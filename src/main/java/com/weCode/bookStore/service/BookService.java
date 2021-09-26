package com.weCode.bookStore.service;

import com.weCode.bookStore.dto.BookDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * @Service is used to indicate that they're holding the business logic.
 * Besides being used in the service layer, there isn't any other special use for this annotation.
 *
 * https://www.baeldung.com/spring-component-repository-service
 */
@Service
public class BookService {

    public List<BookDto> getBooks() {
        return new ArrayList<>();
    }
}
