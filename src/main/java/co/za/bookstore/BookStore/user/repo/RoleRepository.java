package co.za.bookstore.BookStore.user.repo;

import co.za.bookstore.BookStore.user.entity.EnumRole;
import co.za.bookstore.BookStore.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRole name);
}
