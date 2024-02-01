package me.jysh.triply.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a company fleet upload row.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"employeeId", "password", "registrationNumber", "vehicleModel", "admin"})
public class CompanyFleetUploadEntry {

  /**
   * The unique identifier of the employee associated with the company fleet entry.
   */
  private String employeeId;

  /**
   * The password associated with the employee's account in the company fleet system.
   */
  private String password;

  /**
   * The registration number of the vehicle associated with the employee in the company fleet.
   */
  private String registrationNumber;

  /**
   * The model of the vehicle associated with the employee in the company fleet.
   */
  private String vehicleModel;

  /**
   * Indicates whether the employee has administrative privileges in the company fleet system.
   */
  private Boolean admin;
}
