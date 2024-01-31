package me.jysh.triply.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling custom exceptions in the controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles LoginException and returns an UNAUTHORIZED status with a custom error message.
   *
   * @param ex The LoginException that occurred.
   * @return ResponseEntity with UNAUTHORIZED status and error message.
   */
  @ExceptionHandler(LoginException.class)
  public ResponseEntity<String> handleLoginException(LoginException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  /**
   * Handles NotFoundException and returns a NOT_FOUND status with a custom error message.
   *
   * @param ex The NotFoundException that occurred.
   * @return ResponseEntity with NOT_FOUND status and error message.
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * Handles TokenRefreshException and returns a BAD_REQUEST status with a custom error message.
   *
   * @param ex The TokenRefreshException that occurred.
   * @return ResponseEntity with BAD_REQUEST status and error message.
   */
  @ExceptionHandler(TokenRefreshException.class)
  public ResponseEntity<String> handleTokenRefreshException(TokenRefreshException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  /**
   * Handles UnauthenticatedException and returns an UNAUTHORIZED status with a custom error
   * message.
   *
   * @param ex The UnauthenticatedException that occurred.
   * @return ResponseEntity with UNAUTHORIZED status and error message.
   */
  @ExceptionHandler(UnauthenticatedException.class)
  public ResponseEntity<String> handleUnauthenticatedException(UnauthenticatedException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  /**
   * Handles generic Exception and returns an INTERNAL_SERVER_ERROR status with a custom error
   * message.
   *
   * @param ex The Exception that occurred.
   * @return ResponseEntity with INTERNAL_SERVER_ERROR status and error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something went wrong.");
  }
}
