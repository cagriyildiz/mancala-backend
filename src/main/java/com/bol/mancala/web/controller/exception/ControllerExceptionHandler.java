package com.bol.mancala.web.controller.exception;

import com.bol.mancala.web.model.exception.ExceptionInfo;
import com.bol.mancala.web.model.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ExceptionResponse> validationErrorHandler(ConstraintViolationException e,
                                                                  HttpServletRequest request) {
    ExceptionResponse response = buildResponse(e, request.getRequestURI());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  private ExceptionResponse buildResponse(ConstraintViolationException exception, String path) {
    List<ExceptionInfo> exceptions = exception.getConstraintViolations().stream()
        .map(error -> createExceptionInfo(error, path))
        .collect(Collectors.toList());
    return new ExceptionResponse(exceptions);
  }

  private ExceptionInfo createExceptionInfo(ConstraintViolation<?> error, String path) {
    String message = getErrorMessage(error);
    return ExceptionInfo.builder()
        .timestamp(OffsetDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(message)
        .path(path)
        .build();
  }

  private String getErrorMessage(ConstraintViolation<?> error) {
    return error.getPropertyPath() + " " + error.getMessage() + ". Provided: " + error.getInvalidValue();
  }

}
