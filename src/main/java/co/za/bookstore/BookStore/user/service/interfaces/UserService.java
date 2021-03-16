package co.za.bookstore.BookStore.user.service.interfaces;

import co.za.bookstore.BookStore.user.entity.EnumRole;
import co.za.bookstore.BookStore.user.entity.Role;
import co.za.bookstore.BookStore.user.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface UserService {

    User createNewUser(@NotNull User user);

    Set<User> getAllUsers();

    Set<Role> getRoles();

    User getUser(String email);

    Role getRole(EnumRole enumRole);

    Set<Role> getRoles(Set<EnumRole> enumRoles);
}
