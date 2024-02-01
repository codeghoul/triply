package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jysh.triply.config.SecurityContext;

/**
 * Base class for entities in the application, providing common fields and behavior.
 */
@Data
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity {

  /**
   * The timestamp indicating the creation date of the entity.
   */
  @Column(name = "created_at", nullable = false, updatable = false)
  protected Instant createdAt;

  /**
   * The timestamp indicating the last update date of the entity.
   */
  @Column(name = "updated_at", nullable = false)
  protected Instant updatedAt;

  /**
   * The version number of the entity for optimistic locking.
   */
  @Version
  @Column(name = "version")
  protected Long version = 0L;

  /**
   * The user who created the entity.
   */
  @Column(name = "created_by", updatable = false, length = 50)
  private Long createdBy;

  /**
   * The user who last updated the entity.
   */
  @Column(name = "updated_by", length = 50)
  private Long updatedBy;

  /**
   * A flag indicating whether the entity is deleted.
   */
  @Column(name = "is_deleted")
  private boolean deleted;

  /**
   * Executed before the entity is persisted, setting creation and update timestamps, as well as
   * createdBy and updatedBy fields.
   */
  @PrePersist
  protected void onCreate() {
    this.updatedAt = this.createdAt =
        Objects.isNull(this.createdAt) ? Instant.now() : this.createdAt;
    this.updatedBy = this.createdBy = SecurityContext.getLoggedInEmployeeId();
  }

  /**
   * Executed before the entity is updated, updating the updatedAt and updatedBy fields.
   */
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
    this.updatedBy = SecurityContext.getLoggedInEmployeeId();
  }
}

