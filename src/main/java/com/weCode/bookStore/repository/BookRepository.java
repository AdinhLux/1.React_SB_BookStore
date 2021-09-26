package com.weCode.bookStore.repository;

import com.weCode.bookStore.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * CrudRepository is provided by Spring framework and provides us all the functions we need like :
 * <p>
 * - findBooksByTitleAndDescription(String title, String description)
 */
public interface BookRepository extends CrudRepository<Book, UUID> {

}
