package me.jysh.triply.controller;

import me.jysh.triply.config.SecurityContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello! " + SecurityContext.getLoggedInEmployeeId());
    }
}
