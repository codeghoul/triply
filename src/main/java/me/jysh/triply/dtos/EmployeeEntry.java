package me.jysh.triply.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntry {

    private Long id;

    private String username;

    private Long companyId;

    private Collection<String> roles;
}
