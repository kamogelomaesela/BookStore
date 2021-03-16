package co.za.bookstore.BookStore.book.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookForm {

    private String isbn;

    private int quantity;
}
