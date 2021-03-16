package co.za.bookstore.BookStore.common.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @JsonIgnore
    @Version
    private Long version;

    @Getter
    @Setter
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Getter
    @Setter
    @CreatedBy
    @Column(nullable = false)
    private String createdBy;

    @Getter
    @Setter
    @Column(nullable = false)
    private boolean isEnabled = true;
}
