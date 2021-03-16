package co.za.bookstore.BookStore.book.service.interfaces;

import co.za.bookstore.BookStore.book.entity.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public interface BookService {

    Book create(@NotBlank String isbn, int quantity);

    Book update(@Valid Book book);

    Set<Book> getALl();

    Book getById(Long id);

    Book getByIsbn(@NotNull String isbn);

}
