package co.za.bookstore.BookStore.common.exception;

public class IsbnNotFoundException extends ApplicationException {

    public static final int ISBN_NOT_FOUND = 4;

    public static String EMPTY = "Empty";
    public static String NULL = "Null";

    public IsbnNotFoundException(String isbn) {
        super(getMessage(isbn), ISBN_NOT_FOUND);
    }

    private static String getMessage(String isbn) {
        return String.format("Cannot find book with isbn=%s.", isbn);
    }
}