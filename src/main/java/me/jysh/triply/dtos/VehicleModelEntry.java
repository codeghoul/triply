package me.jysh.triply.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jysh.triply.dtos.enums.FuelType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"make", "name", "fuelType", "fuelEmissionFactor"})
public class VehicleModelEntry {

    private Long id;

    private String make;

    private String name;

    private FuelType fuelType;

    private Double fuelEmissionFactor;
}
