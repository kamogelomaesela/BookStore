package co.za.bookstore.BookStore.book.web.dto;

import co.za.bookstore.BookStore.book.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookDto {

    private String isbn;

    private String title;

    private String authors;

    private int quantity;

    public BookDto(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.quantity = book.getQuantity();
    }
}
