package com.weCode.bookStore.integrationTest;

import com.weCode.bookStore.BookStoreApplication;
import com.weCode.bookStore.config.JwtUtil;
import com.weCode.bookStore.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;

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

    // Let's inject JwtUtil
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Set up Header with token (check UserDetailService.java)
    void setUpHeader() {
        String token = jwtUtil.generateToken(new User(
                "peter@gmail.com", passwordEncoder.encode("dummy password"),
                new ArrayList<>()
        ));

        testRestTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + token);

                    return execution.execute(request, body);
                })))
        );
    }

    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookApiCalled() {
        setUpHeader();
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(18);
    }

    /*
     * This one should fail if we call the 2 methods together because Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_1 ON PUBLIC.BOOK(ID) VALUES 2"
     *
     * Solution : annotate this class with @DirtiesContext
     */
    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookApiCalledSecondAttempt() {
        setUpHeader();
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(18);
    }

    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenCalledWithTestTitle() {
        setUpHeader();
        // Let's try the insensitive case : 'test Title' instead of 'test title'
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books/test Title", BookDto[].class);

        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(1);
    }
}
