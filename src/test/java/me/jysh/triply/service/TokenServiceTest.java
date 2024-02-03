package me.jysh.triply.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.RefreshTokenEntry;
import me.jysh.triply.dtos.TokenEntry;
import me.jysh.triply.entity.RefreshTokenEntity;
import me.jysh.triply.exception.TokenRefreshException;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = TokenService.class)
@ExtendWith(SpringExtension.class)
class TokenServiceTest {

  @MockBean
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateTokens() {
    final EmployeeEntry employeeEntry = EmployeeMapper.toEntry(TestMocks.getEmployeeEntity());
    when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(
        getRefreshTokenEntity("refreshToken"));
    TokenEntry result = tokenService.createTokens(employeeEntry);
    assertNotNull(result);
    assertNotNull(result.refreshToken());
    assertNotNull(result.accessToken());
  }
  @Test
  void testValidateAccessTokenInvalid() {
    String accessToken = "invalidAccessToken";
    boolean result = tokenService.validateAccessToken(accessToken);
    assertFalse(result);
  }

  @Test
  void testValidateAccessTokenMalformed() {
    String accessToken = "malformedAccessToken";
    boolean result = tokenService.validateAccessToken(accessToken);
    assertFalse(result);
  }

  @Test
  void testValidateRefreshTokenValid() {
    String refreshToken = "validRefreshToken";
    final RefreshTokenEntity refreshTokenEntity = getRefreshTokenEntity(
        refreshToken);
    when(refreshTokenRepository.findByToken(refreshToken)).thenReturn(
        Optional.of(refreshTokenEntity));
    RefreshTokenEntry result = tokenService.validateRefreshToken(refreshToken);
    assertNotNull(result);
  }

  private static RefreshTokenEntity getRefreshTokenEntity(String refreshToken) {
    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
    refreshTokenEntity.setId(1L);
    refreshTokenEntity.setToken(refreshToken);
    refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(1000000L));
    return refreshTokenEntity;
  }

  @Test
  void testValidateRefreshTokenInvalid() {
    String refreshToken = "invalidRefreshToken";
    when(refreshTokenRepository.findByToken(refreshToken)).thenReturn(Optional.empty());
    assertThrows(TokenRefreshException.class,
        () -> tokenService.validateRefreshToken(refreshToken));
  }

  @Test
  void testValidateRefreshTokenExpired() {
    String refreshToken = "expiredRefreshToken";
    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
    refreshTokenEntity.setToken(refreshToken);
    refreshTokenEntity.setExpiryDate(Instant.now().minusMillis(1000000L));
    when(refreshTokenRepository.findByToken(refreshToken)).thenReturn(
        Optional.of(refreshTokenEntity));
    assertThrows(TokenRefreshException.class,
        () -> tokenService.validateRefreshToken(refreshToken));
  }

  @Test
  void testDeleteRefreshToken() {
    String refreshToken = "validRefreshToken";
    when(refreshTokenRepository.deleteByToken(refreshToken)).thenReturn(1);
    boolean result = tokenService.deleteRefreshToken(refreshToken);
    assertTrue(result);
  }
}
