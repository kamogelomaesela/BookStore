package co.za.bookstore.BookStore.book.web.rest;

import co.za.bookstore.BookStore.book.entity.Book;
import co.za.bookstore.BookStore.book.service.interfaces.BookService;
import co.za.bookstore.BookStore.book.web.dto.BookDto;
import co.za.bookstore.BookStore.book.web.dto.CreateBookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookDto> create(@RequestBody CreateBookForm createBookForm) {
        Book createdBook = bookService.create(createBookForm.getIsbn(), createBookForm.getQuantity());
        return new ResponseEntity<>(new BookDto(createdBook), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<BookDto>> getAll() {
        Set<Book> allBooks = bookService.getALl();
        Set<BookDto> allBooksDto = new HashSet<>();
        allBooks.forEach(book -> allBooksDto.add(new BookDto(book)));
        return ResponseEntity.ok(allBooksDto);
    }

    @GetMapping("/get/{isbn}")
    public ResponseEntity<BookDto> getByIsbn(@PathVariable("isbn") String isbn) {
        Book bookByIsbn = bookService.getByIsbn(isbn);
        return ResponseEntity.ok(new BookDto(bookByIsbn));
    }

}
