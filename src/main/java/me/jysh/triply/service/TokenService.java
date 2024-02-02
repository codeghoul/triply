package me.jysh.triply.service;

import static me.jysh.triply.constant.Constants.COMPANY_ID_CLAIM_KEY;
import static me.jysh.triply.constant.Constants.EMPLOYEE_ID_CLAIM_KEY;
import static me.jysh.triply.constant.Constants.ROLES_CLAIM_KEY;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.RefreshTokenEntry;
import me.jysh.triply.dtos.TokenEntry;
import me.jysh.triply.entity.RefreshTokenEntity;
import me.jysh.triply.exception.TokenRefreshException;
import me.jysh.triply.mappers.RefreshTokenMapper;
import me.jysh.triply.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with {@link RefreshTokenEntity} repository, and managing tokens.
 */
@Service
@Log4j2
public class TokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  @Value("${jwt.access.secret-key}")
  private String jwtAccessSecretKey;

  @Value("${jwt.access.token-expiry-ms}")
  private Long accessTokenExpiryInMs;

  @Value("${jwt.refresh.token-expiry-ms}")
  private Long refreshTokenExpiryInMs;

  @Autowired
  public TokenService(final RefreshTokenRepository repository) {
    this.refreshTokenRepository = repository;
  }

  /**
   * Creates both access and refresh tokens for the given employee.
   *
   * @param employee The employee for whom tokens are generated.
   * @return A TokenEntry containing the generated access and refresh tokens.
   */
  public TokenEntry createTokens(final EmployeeEntry employee) {
    final String refreshToken = createRefreshToken(employee);
    final String accessToken = createAccessToken(employee);
    return new TokenEntry(accessToken, refreshToken);
  }

  private String createAccessToken(EmployeeEntry employee) {
    final Instant now = Instant.now();
    final Instant accessTokenValidity = now.plusMillis(accessTokenExpiryInMs);

    return Jwts.builder()
        .subject(employee.getEmployeeId())
        .claim(EMPLOYEE_ID_CLAIM_KEY, employee.getId())
        .claim(COMPANY_ID_CLAIM_KEY, employee.getCompanyId())
        .claim(ROLES_CLAIM_KEY, employee.getRoles())
        .issuedAt(Date.from(now))
        .expiration(Date.from(accessTokenValidity))
        .signWith(SignatureAlgorithm.HS512, jwtAccessSecretKey)
        .compact();
  }

  private String createRefreshToken(final EmployeeEntry employee) {
    RefreshTokenEntity refreshToken = new RefreshTokenEntity();

    refreshToken.setEmployeeId(employee.getId());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiryInMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken.getToken();
  }

  /**
   * Validates the given access token.
   *
   * @param accessToken The access token to be validated.
   * @return True if the access token is valid; otherwise, false.
   */
  public boolean validateAccessToken(final String accessToken) {
    try {
      getJwtParser().parse(accessToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      return false;
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      return false;
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      return false;
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Retrieves the claims from the given access token.
   *
   * @param accessToken The access token from which claims are to be extracted.
   * @return The claims extracted from the access token.
   */
  public Claims getClaims(final String accessToken) {
    final JwtParser parser = getJwtParser();
    return parser.parseSignedClaims(accessToken).getPayload();
  }

  private JwtParser getJwtParser() {
    return Jwts.parser().setSigningKey(jwtAccessSecretKey).build();
  }

  /**
   * Validates the given refresh token and returns a RefreshTokenEntry if valid.
   *
   * @param refreshToken The refresh token to be validated.
   * @return A RefreshTokenEntry representing the validated refresh token.
   * @throws TokenRefreshException If the refresh token is invalid or expired.
   */
  public RefreshTokenEntry validateRefreshToken(final String refreshToken) {
    final Optional<RefreshTokenEntity> optionalRefreshToken = refreshTokenRepository.findByToken(
        refreshToken);

    final RefreshTokenEntity token = optionalRefreshToken.orElseThrow(
        () ->
            new TokenRefreshException(refreshToken,
                "Refresh token is invalid. Please use a valid refresh token.")
    );

    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(),
          "Refresh token was expired. Please make a new login request.");
    }

    return RefreshTokenMapper.toEntry(token);
  }

  /**
   * Deletes the specified refresh token from the repository.
   *
   * @param refreshToken The refresh token to be deleted.
   * @return True if the refresh token is deleted; otherwise, false.
   */
  public boolean deleteRefreshToken(final String refreshToken) {
    return refreshTokenRepository.deleteByToken(refreshToken) == 1;
  }
}
