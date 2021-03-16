package co.za.bookstore.BookStore.common.exception;

public class ApplicationException extends RuntimeException {

    private final Integer errorCode;

    public ApplicationException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause, Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }
}
