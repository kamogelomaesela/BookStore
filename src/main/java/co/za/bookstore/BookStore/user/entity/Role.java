package co.za.bookstore.BookStore.user.entity;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(name = "uk_role_name", columnNames = "name"))
public class Role extends BaseEntity {

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private EnumRole name;

    public SimpleGrantedAuthority asSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority(name.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
