package co.za.bookstore.BookStore.common.exception;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;
import lombok.Getter;

public class EntityException extends ApplicationException {

    @Getter
    private final Class<? extends BaseEntity> entityClass;

    public EntityException(String message, Integer errorCode, Class<? extends BaseEntity> entityClass) {
        super(message, errorCode);
        this.entityClass = entityClass;
    }

    public EntityException(String message, Throwable cause, Integer errorCode, Class<? extends BaseEntity> entityClass) {
        super(message, cause, errorCode);
        this.entityClass = entityClass;
    }

    public EntityException(Throwable cause, Integer errorCode, Class<? extends BaseEntity> entityClass) {
        super(cause, errorCode);
        this.entityClass = entityClass;
    }
}