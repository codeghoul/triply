package me.jysh.triply.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jysh.triply.dtos.RefreshTokenEntry;
import me.jysh.triply.entity.RefreshTokenEntity;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class RefreshTokenMapper {

  public static RefreshTokenEntry toEntry(final RefreshTokenEntity entity) {
    final RefreshTokenEntry refreshTokenEntry = new RefreshTokenEntry();
    refreshTokenEntry.setId(entity.getId());
    refreshTokenEntry.setToken(entity.getToken());
    refreshTokenEntry.setExpiryDate(entity.getExpiryDate());
    refreshTokenEntry.setEmployeeId(entity.getEmployeeId());
    return refreshTokenEntry;
  }
}
