package me.jysh.triply.controller;

import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.TokenEntry;
import me.jysh.triply.dtos.request.LoginRequest;
import me.jysh.triply.dtos.request.RefreshRequest;
import me.jysh.triply.dtos.response.LoginResponse;
import me.jysh.triply.dtos.response.RefreshResponse;
import me.jysh.triply.facade.AuthFacade;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final AuthFacade facade;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        final LoginResponse loginResponse = facade.login(request.username(), request.password());
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestBody final RefreshRequest request) {
        final RefreshResponse refresh = facade.refresh(request.tokens());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(refresh);
    }
}
