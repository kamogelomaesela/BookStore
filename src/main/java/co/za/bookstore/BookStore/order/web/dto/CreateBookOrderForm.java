package co.za.bookstore.BookStore.order.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookOrderForm {

    private String bookIsbn;

    private int orderQuantity;
}
