package me.jysh.triply.dtos;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.RefreshTokenEntity;

/**
 * Data Transfer Object (DTO) representing a refresh token entry corresponding to a
 * {@link RefreshTokenEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntry {

  /**
   * The unique identifier of the refresh token entry.
   */
  private long id;

  /**
   * The unique identifier of the employee associated with the refresh token.
   */
  private Long employeeId;

  /**
   * The refresh token string.
   */
  private String token;

  /**
   * The expiration date and time of the refresh token.
   */
  private Instant expiryDate;
}
