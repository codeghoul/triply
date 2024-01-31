package me.jysh.triply.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.entity.RoleEntity;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class EmployeeMapper {

    public static EmployeeEntry toEntry(final EmployeeEntity entity) {
        final EmployeeEntry employeeEntry = new EmployeeEntry();
        employeeEntry.setId(entity.getId());
        employeeEntry.setCompanyId(entity.getCompanyId());
        employeeEntry.setUsername(entity.getUsername());
        employeeEntry.setRoles(entity.getRoles().stream().map(RoleEntity::getName).toList());
        return employeeEntry;
    }
}
