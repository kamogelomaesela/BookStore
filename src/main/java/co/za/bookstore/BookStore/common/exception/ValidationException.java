package co.za.bookstore.BookStore.common.exception;

public class ValidationException extends ApplicationException {

    public static final int VALIDATION_FAILED = 3;

    public static String EMPTY = "Empty";
    public static String NULL = "Null";

    public ValidationException(String propertyName, String validation) {
        super(getMessage(propertyName, validation), VALIDATION_FAILED);
    }

    private static String getMessage(String propertyName, String validation) {
        return String.format("%s cannot be %s.", propertyName, validation);
    }

}
