package me.jysh.triply.facade;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthFacade {

  private final EmployeeService employeeService;

  private final TokenService tokenService;

  @Transactional
  public LoginResponse login(final String employeeId, final String password) {
    try {
      final EmployeeEntry employeeEntry = employeeService.findByEmployeeIdAndPassword(employeeId,
          password);
      final TokenEntry tokens = tokenService.createTokens(employeeEntry);
      return new LoginResponse(employeeEntry, tokens);
    } catch (NotFoundException e) {
      throw new LoginException(employeeId);
    }
  }

  @Transactional
  public RefreshResponse refresh(final TokenEntry tokensToRefresh) {
    final RefreshTokenEntry refreshTokenEntry = tokenService.validateRefreshToken(
        tokensToRefresh.refreshToken());
    final EmployeeEntry employee = employeeService.findById(refreshTokenEntry.getEmployeeId());
    final TokenEntry newTokens = tokenService.createTokens(employee);
    final boolean deleted = tokenService.deleteRefreshToken(tokensToRefresh.refreshToken());
    return new RefreshResponse(employee, newTokens);
  }
}
