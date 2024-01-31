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
@JsonPropertyOrder({"employeeID", "distanceTravelled", "energyConsumed", "fuelConsumed"})
public class CompanyFleetMileageUploadEntry {

    private String employeeID;

    private Double distanceTravelled;

    private Double energyConsumed;

    private Double fuelConsumed;
}
