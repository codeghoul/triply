package me.jysh.triply.entity;

import static me.jysh.triply.constant.Constants.SYSTEM;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@MappedSuperclass
public class BaseEntity {

  @Column(name = "created_at", nullable = false, updatable = false)
  protected Instant createdAt;
  @Column(name = "updated_at", nullable = false)
  protected Instant updatedAt;
  @Version
  @Column(name = "version")
  protected Long version = 0L;
  @Column(name = "created_by", updatable = false, length = 50)
  private String createdBy;
  @Column(name = "updated_by", length = 50)
  private String updatedBy;
  @Column(name = "is_deleted")
  private boolean deleted;

  @PrePersist
  protected void onCreate() {
    this.updatedAt = this.createdAt =
        Objects.isNull(this.createdAt) ? Instant.now() : this.createdAt;
    this.updatedBy = this.createdBy = SYSTEM;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
    this.updatedBy = SYSTEM;
  }
}

