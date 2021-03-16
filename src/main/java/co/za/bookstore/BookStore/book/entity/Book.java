package co.za.bookstore.BookStore.book.entity;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;

    private String title;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(nullable = false)
    private int available;

    private String authors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
