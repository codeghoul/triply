package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "vehicle")
@EqualsAndHashCode(callSuper = true)
public class VehicleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String registrationNumber;

    @OneToOne
    @JoinColumn(name = "vehicle_model_id")
    private VehicleModelEntity vehicleModel;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
