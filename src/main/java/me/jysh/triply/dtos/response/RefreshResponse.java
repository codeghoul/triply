package me.jysh.triply.dtos.response;

import me.jysh.triply.dtos.EmployeeEntry;
import me.jysh.triply.dtos.TokenEntry;

/**
 * A record representing RefreshResponse.
 */
public record RefreshResponse(EmployeeEntry employee, TokenEntry tokens) {

}
