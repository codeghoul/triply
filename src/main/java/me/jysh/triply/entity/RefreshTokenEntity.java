package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a refresh token entity in the system.
 */
@Data
@Entity
@Table(name = "refresh_token")
@EqualsAndHashCode(callSuper = true)
public class RefreshTokenEntity extends BaseEntity {

  /**
   * The unique identifier for the refresh token.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The identifier of the employee associated with the refresh token.
   */
  @Column(name = "employee_id")
  private Long employeeId;

  /**
   * The actual refresh token string.
   */
  @Column(name = "token", nullable = false, unique = true)
  private String token;

  /**
   * The expiry date of the refresh token.
   */
  @Column(name = "expiry_date", nullable = false)
  private Instant expiryDate;
}