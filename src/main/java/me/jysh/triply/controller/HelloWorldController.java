package me.jysh.triply.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<String> hello(@PathVariable(value = "name") final String name) {
        return ResponseEntity.ok("Hello! " + name);
    }
}
