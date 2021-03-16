package co.za.bookstore.BookStore.order.entity;

import co.za.bookstore.BookStore.book.entity.Book;
import co.za.bookstore.BookStore.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BookOrder extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String orderReference = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Book orderedBook;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookOrder)) return false;

        BookOrder bookOrder = (BookOrder) o;

        return orderReference.equals(bookOrder.orderReference);
    }

    @Override
    public int hashCode() {
        return orderReference.hashCode();
    }
}
