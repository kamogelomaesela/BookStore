package co.za.bookstore.BookStore.common.exception;

import co.za.bookstore.BookStore.common.entity.base.BaseEntity;

public class EntityConstraintViolationException extends EntityException {

    public static final int ALREADY_EXIST = 2;

    public EntityConstraintViolationException(String property, String propertyValue, Class<? extends BaseEntity> clazz) {
        super(getMessage(property, propertyValue, clazz), ALREADY_EXIST, clazz);
    }

    public EntityConstraintViolationException(Throwable cause, String property, String propertyValue, Class<? extends BaseEntity> clazz) {
        super(getMessage(property, propertyValue, clazz), cause, ALREADY_EXIST, clazz);
    }

    private static String getMessage(String property, String propertyValue, Class<? extends BaseEntity> clazz) {
        return String.format("%s with %s=%s violate constraint.", clazz.getSimpleName(), property, propertyValue);
    }
}
