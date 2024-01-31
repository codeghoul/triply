package me.jysh.triply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
@Data
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @Getter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<EmployeeEntity> employees;
}

