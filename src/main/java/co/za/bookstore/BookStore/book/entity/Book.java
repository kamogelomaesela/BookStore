package co.za.bookstore.BookStore.book.entity;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;
import co.za.bookstore.BookStore.order.entity.BookOrder;
import co.za.bookstore.BookStore.order.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;

    private String title;

    @Column(nullable = false)
    private int quantity = 1;

//    @Column(nullable = false)
//    private int available;

    private String authors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderedBook")
    private List<BookOrder> bookOrders = new ArrayList<>();


    public int getAvailable() {
        return quantity - (bookOrders.stream()
                .filter(bookOrder -> bookOrder.getOrderStatus().equals(OrderStatus.IN_PROGRESS))
                .collect(Collectors.toList()).size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
