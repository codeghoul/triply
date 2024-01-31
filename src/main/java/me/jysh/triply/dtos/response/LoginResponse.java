package me.jysh.triply.dtos.response;

import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.TokenEntry;

/**
 * A record representing LoginResponse.
 */
public record LoginResponse(EmployeeEntry employee, TokenEntry tokens) {

}
