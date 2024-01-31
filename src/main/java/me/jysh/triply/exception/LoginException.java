package me.jysh.triply.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoginException(String employeeId) {
        super(String.format("Login failed for employeeId :: %s, please check credentials or try again.", employeeId));
    }
}

