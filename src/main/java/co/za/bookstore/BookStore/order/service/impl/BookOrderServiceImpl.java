package co.za.bookstore.BookStore.order.service.impl;

import co.za.bookstore.BookStore.book.entity.Book;
import co.za.bookstore.BookStore.book.service.interfaces.BookService;
import co.za.bookstore.BookStore.common.exception.EntityNotFoundException;
import co.za.bookstore.BookStore.common.exception.ValidationException;
import co.za.bookstore.BookStore.order.entity.BookOrder;
import co.za.bookstore.BookStore.order.entity.OrderStatus;
import co.za.bookstore.BookStore.order.repo.BookOrderRepository;
import co.za.bookstore.BookStore.order.service.interfaces.BookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class BookOrderServiceImpl implements BookOrderService {

    private final BookOrderRepository bookOrderRepository;

    private final BookService bookService;

    @Override
    public BookOrder create(String isbn, int quantity) {
        Book book = bookService.getByIsbn(isbn);
        if (quantity > book.getAvailable()) {
            throw new ValidationException("Quantity", "more than number of books available: " + book.getAvailable());
        }
        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderedBook(book);
        bookOrder.setQuantity(quantity);
        bookOrder.setOrderStatus(OrderStatus.IN_PROGRESS);

        return bookOrderRepository.save(bookOrder);
    }

    @Override
    public BookOrder compete(String orderReference) {
        BookOrder bookOrder = getByOrderReference(orderReference);
        if(bookOrder.getOrderStatus().equals(OrderStatus.COMPLETED)){
            throw new ValidationException("Order", "completed again");
        }
        bookOrder.setOrderStatus(OrderStatus.COMPLETED);

        return bookOrderRepository.save(bookOrder);
    }

    @Override
    public Set<BookOrder> getALl() {
        return new HashSet<>(bookOrderRepository.findAll());
    }

    @Override
    public BookOrder getByOrderReference(@NotNull String orderReference) {
        return bookOrderRepository.findByOrderReference(orderReference).orElseThrow(() -> new EntityNotFoundException(Book.class));
    }
}
