package me.jysh.triply.dtos;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntry {

  private Long id;

  private String username;

  private Long companyId;

  private Collection<String> roles;

  private VehicleEntry vehicle;
}
