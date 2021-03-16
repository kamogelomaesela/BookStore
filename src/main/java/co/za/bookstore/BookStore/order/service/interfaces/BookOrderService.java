package co.za.bookstore.BookStore.order.service.interfaces;

import co.za.bookstore.BookStore.order.entity.BookOrder;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface BookOrderService {

    BookOrder create(String isbn, int quantity);

    BookOrder compete(String orderReference);

    Set<BookOrder> getALl();

    BookOrder getByOrderReference(@NotNull String orderReference);

}
