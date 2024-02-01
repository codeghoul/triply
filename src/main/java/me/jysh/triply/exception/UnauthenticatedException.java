package me.jysh.triply.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class representing a unauthenticated exception.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedException extends RuntimeException {

  public UnauthenticatedException(String message) {
    super(message);
  }
}
