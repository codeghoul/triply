package me.jysh.triply.exception;

import me.jysh.triply.dtos.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
  public ResponseEntity<ErrorResponse> handleLoginException(LoginException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(
        new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            ex.getMessage()));
  }

  /**
   * Handles NotFoundException and returns a NOT_FOUND status with a custom error message.
   *
   * @param ex The NotFoundException that occurred.
   * @return ResponseEntity with NOT_FOUND status and error message.
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage()));
  }

  /**
   * Handles TokenRefreshException and returns a BAD_REQUEST status with a custom error message.
   *
   * @param ex The TokenRefreshException that occurred.
   * @return ResponseEntity with BAD_REQUEST status and error message.
   */
  @ExceptionHandler(TokenRefreshException.class)
  public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage()));
  }

  /**
   * Handles UnauthenticatedException and returns an UNAUTHORIZED status with a custom error
   * message.
   *
   * @param ex The UnauthenticatedException that occurred.
   * @return ResponseEntity with UNAUTHORIZED status and error message.
   */
  @ExceptionHandler(UnauthenticatedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthenticatedException(UnauthenticatedException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            ex.getMessage()));
  }

  /**
   * Handles UnauthorizedException and returns an FORBIDDEN status with a custom error message.
   *
   * @param ex The UnauthorizedException that occurred.
   * @return ResponseEntity with FORBIDDEN status and error message.
   */
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthenticatedException(UnauthorizedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        new ErrorResponse(HttpStatus.FORBIDDEN.value(),
            HttpStatus.FORBIDDEN.getReasonPhrase(),
            ex.getMessage()));
  }

  /**
   * Handles DataIntegrityViolationException and returns an CONFLICT status with a custom error
   * message.
   *
   * @param ex The Exception that occurred.
   * @return ResponseEntity with CONFLICT status and error message.
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    String rootMsg = getRootCauseMessage(ex);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        new ErrorResponse(HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            rootMsg));
  }

  @NonNull
  private static String getRootCauseMessage(@NonNull Throwable t) {
    Throwable rootCause = t;
    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
      rootCause = rootCause.getCause();
    }
    return rootCause.getMessage();
  }

  /**
   * Handles BadRequestException and returns an BAD_REQUEST status with a custom error message.
   *
   * @param ex The Exception that occurred.
   * @return ResponseEntity with BAD_REQUEST status and error message.
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage()));
  }

  /**
   * Handles generic Exception and returns an INTERNAL_SERVER_ERROR status with a custom error
   * message.
   *
   * @param ex The Exception that occurred.
   * @return ResponseEntity with INTERNAL_SERVER_ERROR status and error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage()));
  }
}
