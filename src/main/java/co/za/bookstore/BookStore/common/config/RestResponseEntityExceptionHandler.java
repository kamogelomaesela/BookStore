package co.za.bookstore.BookStore.common.config;

import co.za.bookstore.BookStore.common.exception.EntityConstraintViolationException;
import co.za.bookstore.BookStore.common.exception.EntityNotFoundException;
import co.za.bookstore.BookStore.common.exception.IsbnNotFoundException;
import co.za.bookstore.BookStore.common.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        ApiErrorDTO apiError =
                new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleAll(Throwable ex, WebRequest request) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error Occurred", "Internal Error Occurred");

        logger.error("Unknown error occured");
        logger.error(ex.getLocalizedMessage(), ex);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "Entity Not Found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({IsbnNotFoundException.class})
    public ResponseEntity<Object> handleIsbnNotFoundException(Exception ex, WebRequest request) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "ISBN Not Found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "Email of password incorrect");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ValidationException.class, EntityConstraintViolationException.class})
    public ResponseEntity<Object> handleValidationExceptionAndEntityConstraintViolationException(Exception ex, WebRequest request) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

        logger.warn(ex.getMessage());

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String name = ex.getRequiredType() != null ? ex.getRequiredType().getName() : "null";
        String error = ex.getName() + " should be of type " + name;

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("Bad Request", ex);
        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, "Error in your request", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


}
