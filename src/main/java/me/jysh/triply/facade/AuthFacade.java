package me.jysh.triply.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.RefreshTokenEntry;
import me.jysh.triply.dtos.TokenEntry;
import me.jysh.triply.dtos.response.LoginResponse;
import me.jysh.triply.dtos.response.RefreshResponse;
import me.jysh.triply.exception.LoginException;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthFacade {

  private final EmployeeService employeeService;
  private final TokenService tokenService;

  /**
   * Performs login for the given employeeId and password.
   *
   * @param employeeId The employee ID.
   * @param password   The password.
   * @return A LoginResponse containing employee details and tokens upon successful login.
   * @throws LoginException If login fails due to a NotFoundException.
   */
  @Transactional
  public LoginResponse login(final String employeeId, final String password) {
    try {
      final EmployeeEntry employeeEntry = employeeService.findByEmployeeIdAndPassword(employeeId,
          password);
      final TokenEntry tokens = tokenService.createTokens(employeeEntry);
      log.info("Successful login for employeeId: {}", employeeId);
      return new LoginResponse(employeeEntry, tokens);
    } catch (NotFoundException e) {
      log.error("Login failed for employeeId: {}. Employee not found.", employeeId);
      throw new LoginException(employeeId);
    }
  }

  /**
   * Refreshes tokens using the provided refresh token.
   *
   * @param tokensToRefresh The tokens to be refreshed.
   * @return A RefreshResponse containing updated tokens.
   */
  @Transactional
  public RefreshResponse refresh(final TokenEntry tokensToRefresh) {
    final RefreshTokenEntry refreshTokenEntry = tokenService.validateRefreshToken(
        tokensToRefresh.refreshToken());
    final EmployeeEntry employee = employeeService.findById(refreshTokenEntry.getEmployeeId());
    final TokenEntry newTokens = tokenService.createTokens(employee);
    final boolean deleted = tokenService.deleteRefreshToken(tokensToRefresh.refreshToken());

    if (deleted) {
      log.info("Refreshed tokens for employeeId: {}", employee.getEmployeeId());
    } else {
      log.warn("Failed to delete refresh token for employeeId: {}", employee.getEmployeeId());
    }

    return new RefreshResponse(employee, newTokens);
  }
}
