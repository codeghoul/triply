package me.jysh.triply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents an employee entity in the system.
 */
@Data
@Entity
@Table(name = "employee", uniqueConstraints = {
    @UniqueConstraint(name = "uk_employee", columnNames = {"companyId", "username"})
})
@EqualsAndHashCode(callSuper = true)
public class EmployeeEntity extends BaseEntity {

  /**
   * The unique identifier for the employee.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The username of the employee.
   */
  @Column(name = "username")
  private String username;

  /**
   * The password associated with the employee's account.
   */
  @Column(name = "password")
  private String password;

  /**
   * The identifier of the company to which the employee belongs.
   */
  @Column(name = "company_id")
  private Long companyId;

  /**
   * The roles assigned to the employee.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "employee_role",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Collection<RoleEntity> roles;

  /**
   * The vehicle associated with the employee.
   */
  @OneToOne
  @JoinColumn(name = "vehicle_id")
  private VehicleEntity vehicle;
}
