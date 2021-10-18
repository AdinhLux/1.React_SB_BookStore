package com.weCode.bookStore.repository;

import com.weCode.bookStore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.StreamSupport;

/*
You can run the test in the Terminal with './gradlew check'
 * @SpringExtension introduced in Spring 5, is used to integrate Spring TestContext with JUnit 5 Jupiter Test.
 * https://rieckpil.de/what-the-heck-is-the-springextension-used-for/
 *
 * @DataJpaTest is used to test JPA repositories.
 * It is used in combination with @RunWith(SpringRunner.class).
 * The annotation disables full auto-configuration and applies only configuration relevant to JPA tests.
 * By default, tests annotated with @DataJpaTest use an embedded in-memory database.
 * https://zetcode.com/springboot/datajpatest/
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldAbleToFetchAllBooksInDB() {
        Iterable<Book> all = bookRepository.findAll();
        /*
         * With the Java 8 Stream API, it is possible to concisely declare processes on a data source which will be executed sequentially or in parallel.
         * See this URL for performance : https://stackoverflow.com/questions/22658322/java-8-performance-of-streams-vs-collections
         */
        Long totalBookAccount = StreamSupport.stream(all.spliterator(), false).count();
        System.out.println("shouldAbleToFetchAllBooksInDB() totalBookAccount = " + totalBookAccount);
        Assertions.assertEquals(totalBookAccount, 18);
    }

    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenTitleIsTestTitle() {
        List<Book> testTitleBooks = bookRepository.findBooksByTitle("test title");
        Assertions.assertEquals(testTitleBooks.size(), 1);
    }

    @Test
    @Sql(scripts = {"classpath:insertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenTitleIsTestTitleIgnoreCase() {
        List<Book> testTitleBooks = bookRepository.findBooksByTitleIgnoreCase("Test Title");
        Assertions.assertEquals(testTitleBooks.size(), 1);
    }
}
