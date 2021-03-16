package co.za.bookstore.BookStore.common.exception;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;

public class EntityNotFoundException extends EntityException {

    public static final int ENTITY_NOT_FOUND = 1;

    public EntityNotFoundException(Class<? extends BaseEntity> clazz) {
        super(getMessage(clazz), ENTITY_NOT_FOUND, clazz);
    }

    public EntityNotFoundException(Throwable cause, Class<? extends BaseEntity> clazz) {
        super(getMessage(clazz), cause, ENTITY_NOT_FOUND, clazz);
    }

    private static String getMessage(Class<? extends BaseEntity> clazz) {
        return String.format("%s was not found", clazz.getSimpleName());
    }
}
