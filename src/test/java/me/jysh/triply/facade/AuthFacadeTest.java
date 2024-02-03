package me.jysh.triply.facade;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.RefreshTokenEntry;
import me.jysh.triply.dtos.TokenEntry;
import me.jysh.triply.dtos.response.LoginResponse;
import me.jysh.triply.dtos.response.RefreshResponse;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.exception.LoginException;
import me.jysh.triply.exception.NotFoundException;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.service.EmployeeService;
import me.jysh.triply.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = AuthFacade.class)
@ExtendWith(SpringExtension.class)
class AuthFacadeTest {

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private TokenService tokenService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthFacade authFacade;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLogin_Successful() {
    String employeeId = "john_doe";
    String password = "password";
    EmployeeEntity employeeEntity = TestMocks.getEmployeeEntity();

    when(employeeService.findByUsername(employeeId)).thenReturn(employeeEntity);
    when(passwordEncoder.matches(password, employeeEntity.getPassword())).thenReturn(true);
    when(tokenService.createTokens(any(EmployeeEntry.class))).thenReturn(
        new TokenEntry("accessToken", "refreshToken"));

    LoginResponse result = authFacade.login(employeeId, password);

    assertNotNull(result);
    assertNotNull(result.employee());
    assertNotNull(result.tokens());
    verify(tokenService, times(1)).createTokens(any(EmployeeEntry.class));
  }

  @Test
  void testLogin_Failure_NotFoundException() {
    String employeeId = "john.doe";
    String password = "password";

    when(employeeService.findByUsername(employeeId)).thenThrow(new NotFoundException(employeeId));

    assertThrows(LoginException.class, () -> authFacade.login(employeeId, password));
  }

  @Test
  void testLogin_Failure_WrongPassword() {
    String employeeId = "john.doe";
    String password = "wrong_password";
    EmployeeEntity employeeEntity = TestMocks.getEmployeeEntity();

    when(employeeService.findByUsername(employeeId)).thenReturn(employeeEntity);
    when(passwordEncoder.matches(password, employeeEntity.getPassword())).thenReturn(false);

    assertThrows(LoginException.class, () -> authFacade.login(employeeId, password));
  }

  @Test
  void testRefresh_Successful() {
    TokenEntry tokensToRefresh = new TokenEntry("accessToken", "refreshToken");
    RefreshTokenEntry refreshTokenEntry = new RefreshTokenEntry(1L, 1L, "refreshToken",
        Instant.now());

    when(tokenService.validateRefreshToken(tokensToRefresh.refreshToken())).thenReturn(
        refreshTokenEntry);

    final EmployeeEntity employeeEntity = TestMocks.getEmployeeEntity();
    final EmployeeEntry employeeEntry = EmployeeMapper.toEntry(employeeEntity);

    when(employeeService.findById(refreshTokenEntry.getEmployeeId())).thenReturn(employeeEntry);

    TokenEntry newTokens = new TokenEntry("newAccessToken", "newRefreshToken");
    when(tokenService.createTokens(employeeEntry)).thenReturn(newTokens);
    when(tokenService.deleteRefreshToken(tokensToRefresh.refreshToken())).thenReturn(true);

    RefreshResponse result = authFacade.refresh(tokensToRefresh);

    assertNotNull(result);
    assertNotNull(result.employee());
    assertNotNull(result.tokens());
    verify(tokenService, times(1)).createTokens(any(EmployeeEntry.class));
    verify(tokenService, times(1)).deleteRefreshToken(tokensToRefresh.refreshToken());
  }

  @Test
  void testRefresh_Failure_InvalidRefreshToken() {
    TokenEntry tokensToRefresh = new TokenEntry("accessToken", "refreshToken");
    when(tokenService.validateRefreshToken(tokensToRefresh.refreshToken())).thenThrow(
        new LoginException("InvalidRefreshToken"));
    assertThrows(LoginException.class, () -> authFacade.refresh(tokensToRefresh));
  }
}
