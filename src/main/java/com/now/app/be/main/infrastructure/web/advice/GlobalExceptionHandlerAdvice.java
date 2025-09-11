package com.now.app.be.main.infrastructure.web.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.now.app.be.main.domain.exception.NotFoundException;
import com.now.app.be.main.infrastructure.web.response.dto.DetailErrorDto;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException notFound) {
    return new ResponseEntity<>(DetailErrorDto.builder().localDateTime(LocalDateTime.now())
        .code(HttpStatus.NOT_FOUND.value()).message(notFound.getMessage()).build(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(ConstraintViolationException notFound) {
    return new ResponseEntity<>(DetailErrorDto.builder().localDateTime(LocalDateTime.now())
        .code(HttpStatus.BAD_REQUEST.value()).message(notFound.getMessage()).build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
    return new ResponseEntity<>(
        DetailErrorDto.builder().localDateTime(LocalDateTime.now()).code(HttpStatus.BAD_REQUEST.value())
            .message("Required parameter '" + ex.getParameterName() + "' is not present.").build(),
        HttpStatus.BAD_REQUEST);
  }

}
