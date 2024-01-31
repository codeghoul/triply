package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a role entity in the system.
 */
@Entity
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleEntity extends BaseEntity {

  /**
   * The unique identifier for the role.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The name of the role.
   */
  @Column(name = "name")
  private String name;

  /**
   * The collection of employees associated with this role, mustn't be accessed, just for making JPA
   * annotation work.
   */
  @Getter(AccessLevel.NONE)
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private Collection<EmployeeEntity> employees;
}