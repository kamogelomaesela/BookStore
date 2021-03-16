package co.za.bookstore.BookStore.user.web.dto;

import co.za.bookstore.BookStore.user.entity.EnumRole;
import co.za.bookstore.BookStore.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String name;

    private String email;

    private String password;

    private Set<EnumRole> roles;

    @JsonIgnore
    public User toUser() {
        return new User(name, email, password);
    }

}
