package com.weCode.bookStore.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity : Represents the DB table
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    /**
     * If we want the primary key value to be generated automatically for us, we can add the @GeneratedValue annotation.
     *
     * strategy : (Optional) The primary key generation strategy that the persistence provider must use to generate the annotated entity primary key.
     * generator : (Optional) The name of the primary key generator to use as specified in the SequenceGenerator or TableGenerator annotation.
     *
     *
     * AUTO Generation
     * ---------------
     * If we're using the default generation type, the persistence provider will determine values based on the type of the primary key attribute. This type can be numerical or UUID.
     *
     * For numeric values, the generation is based on a sequence or table generator, while UUID values will use the UUIDGenerator.
     *
     * https://www.baeldung.com/hibernate-identifiers
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private int releaseYear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
