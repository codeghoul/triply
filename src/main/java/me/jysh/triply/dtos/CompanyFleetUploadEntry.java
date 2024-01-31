package me.jysh.triply.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"employeeId", "password", "registrationNumber", "vehicleModel", "admin"})
public class CompanyFleetUploadEntry {

  private String employeeId;

  private String password;

  private String registrationNumber;

  private String vehicleModel;

  private Boolean admin;
}
