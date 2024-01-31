package me.jysh.triply.dtos;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntry {

  private long id;

  private Long employeeId;

  private String token;

  private Instant expiryDate;
}
