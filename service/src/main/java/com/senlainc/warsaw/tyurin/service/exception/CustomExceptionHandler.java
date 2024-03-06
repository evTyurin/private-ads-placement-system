package com.senlainc.warsaw.tyurin.service.exception;

import com.senlainc.warsaw.tyurin.enums.ExceptionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller for handling exceptions
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle situation when field value filled with incorrect type
     *
     * @param exception the HttpMessageNotReadableException exception
     * @param headers   the HttpHeaders headers
     * @param status    the HttpStatus status
     * @param request   the WebRequest request
     * @return custom exception info and http status
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.readable.message",
                null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle situation when field value filled with invalid data
     *
     * @param exception the MethodArgumentNotValidException exception
     * @param headers   the HttpHeaders headers
     * @param status    the HttpStatus status
     * @param request   the WebRequest request
     * @return custom exception info and http status
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ResponseException> responseExceptions = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(objectError ->
                responseExceptions.add(new ResponseException(messageSource
                        .getMessage(Objects.requireNonNull(objectError.getDefaultMessage()),
                                new Object[]{objectError.getField(), objectError.getRejectedValue()}, LocaleContextHolder.getLocale()),
                        ExceptionInfo.getExceptionCodeByMessage(objectError.getDefaultMessage()))));
        return new ResponseEntity<>(responseExceptions, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle situation when entity could not be found by name/email
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = NotFoundByNameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseException> handleNotFoundByNameException(NotFoundByNameException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.found.name.message",
                new Object[]{exception.getName()}, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle situation when entity could not be found
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = NotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseException> handleNotFoundById(NotFoundByIdException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.found.id.message",
                new Object[]{exception.getId()}, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle situation when parameters in URL are incorrect
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = InvalidUrlParameterException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseException> handleInvalidUrlParameterException(InvalidUrlParameterException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("invalid.url.parameter.message",
                new Object[]{exception.getName()}, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handle the situation when user don't have enough money to pay for advertisement being in top
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = NotEnoughBalanceMoneyException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseException> handleNotEnoughBalanceMoneyException(NotEnoughBalanceMoneyException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.enough.money.exception",
                new Object[]{exception.getPaidSum()}, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handle situation when login or password incorrect
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseException> handleAuthExceptionException(AuthException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("authentication.failed.exception",
                null, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle situation when entity could not be added. It's already exist
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseException> handleEntityExistException(EntityExistException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("entity.exist.message",
                null, LocaleContextHolder.getLocale()), exception.getExceptionCode()),
                HttpStatus.CONFLICT);
    }

    /**
     * Handle situation when parameters in URL are incorrect
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = ExpectationFailedException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseException> handleExpectationFailedException(ExpectationFailedException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("expectation.failed.message",
                null, LocaleContextHolder.getLocale()), exception.getExceptionCode()),
                HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handle @PathVariable incorrect param
     *
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseException> handleMethodArgumentTypeMismatch() {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("invalid.argument.message",
                null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle situation when exception happens during sending the email
     *
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = MessagingException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ResponseException> handleMessagingException() {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.send.email",
                null, LocaleContextHolder.getLocale()), HttpStatus.NOT_ACCEPTABLE.value()),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
