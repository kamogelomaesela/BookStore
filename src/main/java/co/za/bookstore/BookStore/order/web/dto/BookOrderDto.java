package co.za.bookstore.BookStore.order.web.dto;

import co.za.bookstore.BookStore.order.entity.BookOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderDto {

    private String bookIsbn;

    private int orderQuantity;

    private String orderReference;

    private String bookTitle;

    private String bookAuthors;

    public BookOrderDto(BookOrder bookOrder) {
        this.bookIsbn  = bookOrder.getOrderedBook().getIsbn();
        this.orderQuantity = bookOrder.getQuantity();
        this.orderReference = bookOrder.getOrderReference();
        this.bookTitle  = bookOrder.getOrderedBook().getTitle();
        this.bookAuthors  = bookOrder.getOrderedBook().getAuthors();
    }
}
