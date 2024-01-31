package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.entity.CompanyEntity;

/**
 * Data Transfer Object (DTO) representing a company entry corresponding to a
 * {@link CompanyEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntry {

  /**
   * Unique identifier for company.
   */
  private Long id;

  /**
   * Name of the company.
   */
  private String name;
}
