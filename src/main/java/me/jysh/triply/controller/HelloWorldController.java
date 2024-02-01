package me.jysh.triply.controller;

import me.jysh.triply.config.PreAuthorize;
import me.jysh.triply.config.SecurityContext;
import me.jysh.triply.constant.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

  @GetMapping(value = "", produces = "application/json")
  @PreAuthorize(withRoles = {"ROLE_NOT_DEFINED"})
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello! " + SecurityContext.getLoggedInEmployeeId());
  }
}
