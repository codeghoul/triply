package me.jysh.triply.service;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.entity.EmployeeEntity;
import me.jysh.triply.exception.EmployeeNotFoundException;
import me.jysh.triply.mappers.EmployeeMapper;
import me.jysh.triply.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeEntry findByEmployeeIdAndPassword(final String username, final String password) {
        final Optional<EmployeeEntity> optionalEmployee = repository.findByUsernameAndPassword(username, password);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException(username);
        }

        return optionalEmployee.map(EmployeeMapper::toEntry).get();
    }

    public EmployeeEntry findById(final Long employeeId) {
        return repository.findById(employeeId).map(EmployeeMapper::toEntry).orElseThrow(
                () -> new EmployeeNotFoundException(employeeId)
        );
    }
}
