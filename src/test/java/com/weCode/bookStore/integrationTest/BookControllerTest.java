package com.weCode.bookStore.integrationTest;

import com.weCode.bookStore.BookStoreApplication;
import com.weCode.bookStore.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * @DirtiesContext : after each test method, we clean up the DB
 */
@SpringBootTest(classes = BookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookApiCalled() {
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(2);
    }

    /*
     * This one should fail if we call the 2 methods together because Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_1 ON PUBLIC.BOOK(ID) VALUES 2"
     *
     * Solution : annotate this class with @DirtiesContext
     */
    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookApiCalledSecondAttempt() {
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(2);
    }
}
