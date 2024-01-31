package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Month;
import java.time.Year;

@Data
@Entity
@Table(name = "mileage")
@EqualsAndHashCode(callSuper = true)
public class MileageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long vehicleId;

    private Double distanceTravelled;

    private Double energyConsumed;

    private Double fuelConsumed;

    private Year year;

    private Month month;

    private Integer week;
}
