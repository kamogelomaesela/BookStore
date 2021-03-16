package co.za.bookstore.BookStore.order.repo;

import co.za.bookstore.BookStore.order.entity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    Optional<BookOrder> findByOrderReference(String orderReference);

}
