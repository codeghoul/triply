package me.jysh.triply.dtos;

/**
 * A record representing a pair of access and refresh tokens.
 */
public record TokenEntry(String accessToken, String refreshToken) {

}
