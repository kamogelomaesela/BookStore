package co.za.bookstore.BookStore.book.service.impl;

import co.za.bookstore.BookStore.book.entity.Book;
import co.za.bookstore.BookStore.book.repo.BookRepository;
import co.za.bookstore.BookStore.book.service.interfaces.BookService;
import co.za.bookstore.BookStore.common.exception.EntityConstraintViolationException;
import co.za.bookstore.BookStore.common.exception.EntityNotFoundException;
import co.za.bookstore.BookStore.common.exception.IsbnNotFoundException;
import co.za.bookstore.BookStore.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final RestTemplate restTemplate;

    @Override
    public Book create(String isbn, int quantity) {
        Book existingBook = getByIsbn(isbn);
        if (existingBook != null)
            throw new EntityConstraintViolationException("isbn", isbn, Book.class);
        if (quantity < 1) {
            throw new ValidationException("Quantity", "less than 1");
        }
        try {
            JSONArray jsonObjectItems = getJSONObjectByIsbn(isbn).getJSONArray("items");
            Book book = new Book();
            book.setIsbn(isbn);
            book.setQuantity(quantity);
            book.setAvailable(quantity);
            book.setTitle(getBookTitle(jsonObjectItems));
            book.setAuthors(String.join(", ", getBookEditionAuthors(jsonObjectItems)));
            return bookRepository.save(book);
        } catch (RuntimeException e) {
            throw new IsbnNotFoundException(isbn);
        }
    }

    @Override
    public Book update(@Valid Book book) {
        Book existingBook = getByIsbn(book.getIsbn());
        if (book.getAvailable() > existingBook.getQuantity()) {
            throw new ValidationException("Book Availability", "greater than book quantity");
        }
        existingBook.setAvailable(book.getAvailable());
        return bookRepository.save(existingBook);
    }

    @Override
    public Set<Book> getALl() {
        return new HashSet<>(bookRepository.findAll());
    }

    @Override
    public Book getById(@NotNull Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Book.class));
    }

    @Override
    public Book getByIsbn(@NotNull String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    private JSONObject getJSONObjectByIsbn(String isbn) {
        String jsonObject = restTemplate.getForObject(defaultURLOnIsbnAndBookPrintType(isbn), String.class);
        return new JSONObject(jsonObject);
    }

    private String defaultURLOnIsbnAndBookPrintType(String isbn) {
        final String webServiceEndPoint = "https://www.googleapis.com/books/v1/volumes?q=";
        final String isbnParameterSearch = "isbn:" + isbn;
        final String maxResultsParameter = "&maxResults=1";
        final String printTypeParameter = "&printType=books";

        return webServiceEndPoint + isbnParameterSearch + maxResultsParameter + printTypeParameter;
    }

    private String getBookTitle(JSONArray jsonObjectItems) throws JSONException {
        return getVolumInfo(jsonObjectItems).getString("title");
    }

    private String[] getBookEditionAuthors(JSONArray jsonObjectItems) throws JSONException {
        return getVolumInfo(jsonObjectItems).getJSONArray("authors").toString().replaceAll("\"|\\[|]", "")
                .split(",\\s*");
    }

    private JSONObject getVolumInfo(JSONArray jsonObjectItems) {
        return jsonObjectItems.getJSONObject(0).getJSONObject("volumeInfo");
    }
}
