package me.jysh.triply.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(final Long id) {
        super(String.format("Employee not found for id :: %d", id));
    }

    public EmployeeNotFoundException(final String employeeExternalId) {
        super(String.format("Employee not found for externalId :: %s", employeeExternalId));
    }
}
