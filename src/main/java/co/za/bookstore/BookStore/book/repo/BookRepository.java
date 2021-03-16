package co.za.bookstore.BookStore.book.repo;

import co.za.bookstore.BookStore.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);

}
