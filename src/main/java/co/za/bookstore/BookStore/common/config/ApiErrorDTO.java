package co.za.bookstore.BookStore.common.config;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
public class ApiErrorDTO {

    private HttpStatus status;
    private Integer statusCode;
    private String message;
    private List<String> errors;

    public ApiErrorDTO(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.statusCode = status.value();
    }

    public ApiErrorDTO(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
        this.statusCode = status.value();
    }
}
