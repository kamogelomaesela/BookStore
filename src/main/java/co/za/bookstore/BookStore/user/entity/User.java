package co.za.bookstore.BookStore.user.entity;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "system_user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    @NotBlank
    @JsonIgnore
    private String password;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, @NotBlank @Email String email, @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
