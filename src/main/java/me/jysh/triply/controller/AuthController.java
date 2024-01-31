package me.jysh.triply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.dtos.request.LoginRequest;
import me.jysh.triply.dtos.request.RefreshRequest;
import me.jysh.triply.dtos.response.LoginResponse;
import me.jysh.triply.dtos.response.RefreshResponse;
import me.jysh.triply.facade.AuthFacade;
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

    @Operation(summary = "Login to the system", description = "Authenticate user and generate an access token.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        final LoginResponse loginResponse = facade.login(request.username(), request.password());
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "Refresh access token", description = "Refresh the access token using a valid refresh token.")
    @ApiResponse(
            responseCode = "202",
            description = "Token refreshed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshResponse.class))
    )
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(
            @Parameter(description = "Tokens for refreshing", required = true)
            @RequestBody RefreshRequest request
    ) {
        final RefreshResponse refresh = facade.refresh(request.tokens());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(refresh);
    }
}
