package me.jysh.triply.dtos.response;

import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.TokenEntry;

public record LoginResponse(EmployeeEntry employee, TokenEntry tokens) {
}
