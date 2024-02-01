package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a company in the application.
 */
@Data
@Entity
@Table(name = "company")
@EqualsAndHashCode(callSuper = true)
public class CompanyEntity extends BaseEntity {

  /**
   * The unique identifier for the company.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The name of the company.
   */
  @Column(name = "name", nullable = false)
  private String name;
}
