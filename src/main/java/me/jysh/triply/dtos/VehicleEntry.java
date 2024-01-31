package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntry {

    private Long id;

    private String registrationNumber;

    private VehicleModelEntry vehicleModel;

    private Long employeeId;
}
